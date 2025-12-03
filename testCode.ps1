# 파일명: send_requests_job.ps1

$API_ENDPOINT = "http://localhost:8080/traffic"
$TOTAL_REQUESTS = 1000      # 1000 건
$BATCH_UNIT = 1000           # 1000개씩 배치 처리
$BATCH_SIZE = 50             # 동시 50개 Job
$MAX_RETRY = 3
$DELAY_BETWEEN_BATCH = 100
$DELAY_BETWEEN_RETRY = 50

# 진행 상황 추적
$startTime = Get-Date
$completedCount = 0

for ($batchStart = 1; $batchStart -le $TOTAL_REQUESTS; $batchStart += $BATCH_UNIT) {
    $batchEnd = [Math]::Min($batchStart + $BATCH_UNIT - 1, $TOTAL_REQUESTS)
    $batchRange = $batchStart..$batchEnd

    Write-Host "=== 배치 시작: $batchStart ~ $batchEnd ==="

    $jobs = @()

    foreach ($userId in $batchRange) {
        while ($jobs.Count -ge $BATCH_SIZE) {
            $jobs = $jobs | Where-Object { $_.State -eq 'Running' }
            Start-Sleep -Milliseconds 50
        }

        $jobs += Start-Job -ArgumentList $userId -ScriptBlock {
            param($userId)

            # Job 내부에서 어셈블리 로드
            Add-Type -AssemblyName System.Net.Http

            $API_ENDPOINT = "http://localhost:8080/traffic"
            $MAX_RETRY = 3
            $DELAY_BETWEEN_RETRY = 50

            $success = $false
            $attempt = 0
            $client = [System.Net.Http.HttpClient]::new()
            $body = @{ id = $userId; name = "kim$userId" } | ConvertTo-Json

            while (-not $success -and $attempt -lt $MAX_RETRY) {
                try {
                    $content = [System.Net.Http.StringContent]::new($body, [System.Text.Encoding]::UTF8, "application/json")
                    $response = $client.PostAsync($API_ENDPOINT, $content).Result
                    $success = $true
                } catch {
                    $attempt++
                    if ($attempt -ge $MAX_RETRY) {
                        Write-Error "실패: $userId"
                    }
                    Start-Sleep -Milliseconds $DELAY_BETWEEN_RETRY
                }
            }

            $client.Dispose()
        }
    }

    $jobs | Wait-Job | Out-Null
    $jobs | Receive-Job
    $jobs | Remove-Job

    $completedCount += ($batchEnd - $batchStart + 1)
    $progress = [math]::Round(($completedCount / $TOTAL_REQUESTS) * 100, 2)
    $elapsed = (Get-Date) - $startTime

    Write-Host "=== 배치 완료: $batchStart ~ $batchEnd | 진행률: $progress% | 경과 시간: $($elapsed.ToString('hh\:mm\:ss')) ==="

    Start-Sleep -Milliseconds $DELAY_BETWEEN_BATCH
}

$totalTime = (Get-Date) - $startTime
Write-Host "`n========================================="
Write-Host "전체 완료: $TOTAL_REQUESTS 건"
Write-Host "총 소요 시간: $($totalTime.ToString('hh\:mm\:ss'))"
Write-Host "========================================="