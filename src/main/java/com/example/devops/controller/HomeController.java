package com.example.devops.controller;

import com.example.devops.service.StatusService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    private final StatusService statusService;

    public HomeController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String homeHtml() {
        String appName = statusService.getApplicationName();
        String welcomeMsg = statusService.getWelcomeMessage();
        String status = statusService.getStatus();

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>%s - DevOps Dashboard</title>
                    <style>
                        :root {
                            --bg-primary: #0a0e17;
                            --bg-card: rgba(18, 24, 38, 0.85);
                            --accent-blue: #0c7a24ff;
                            --accent-cyan: #06b6d4;
                            --accent-green: #10b981;
                            --text-primary: #f8fafc;
                            --text-muted: #94a3b8;
                            --border-color: rgba(255, 255, 255, 0.08);
                        }
                        * { box-sizing: border-box; margin: 0; padding: 0; }
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
                            background: radial-gradient(circle at 50%% 0%%, #172554, #0a0e17 65%%);
                            color: var(--text-primary);
                            min-height: 100vh;
                            padding: 2.5rem 1.5rem;
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                        }
                        .container {
                            max-width: 960px;
                            width: 100%%;
                        }
                        .header {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            margin-bottom: 2rem;
                            padding-bottom: 1.5rem;
                            border-bottom: 1px solid var(--border-color);
                        }
                        .logo-wrap {
                            display: flex;
                            align-items: center;
                            gap: 12px;
                        }
                        .logo-icon {
                            width: 44px;
                            height: 44px;
                            background: linear-gradient(135deg, var(--accent-blue), var(--accent-cyan));
                            border-radius: 12px;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            font-weight: 800;
                            font-size: 1.4rem;
                            color: #fff;
                            box-shadow: 0 4px 20px rgba(59, 130, 246, 0.35);
                        }
                        .badge {
                            display: inline-flex;
                            align-items: center;
                            gap: 8px;
                            background: rgba(16, 185, 129, 0.15);
                            border: 1px solid rgba(16, 185, 129, 0.3);
                            color: #34d399;
                            padding: 6px 14px;
                            border-radius: 9999px;
                            font-size: 0.85rem;
                            font-weight: 600;
                        }
                        .dot {
                            width: 9px;
                            height: 9px;
                            background: #10b981;
                            border-radius: 50%%;
                            box-shadow: 0 0 10px #10b981;
                            animation: pulse 2s infinite;
                        }
                        @keyframes pulse {
                            0%% { transform: scale(0.95); opacity: 0.8; }
                            50%% { transform: scale(1.2); opacity: 1; }
                            100%% { transform: scale(0.95); opacity: 0.8; }
                        }
                        .hero {
                            background: var(--bg-card);
                            border: 1px solid var(--border-color);
                            backdrop-filter: blur(12px);
                            border-radius: 20px;
                            padding: 2.5rem;
                            margin-bottom: 2rem;
                            box-shadow: 0 20px 40px rgba(0,0,0,0.4);
                            text-align: center;
                        }
                        .hero h1 {
                            font-size: 2.3rem;
                            margin-bottom: 0.75rem;
                            background: linear-gradient(to right, #ffffff, #93c5fd);
                            -webkit-background-clip: text;
                            -webkit-text-fill-color: transparent;
                        }
                        .hero p {
                            font-size: 1.15rem;
                            color: var(--text-muted);
                            max-width: 600px;
                            margin: 0 auto;
                        }
                        .metrics-grid {
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                            gap: 1.25rem;
                            margin-bottom: 2rem;
                        }
                        .metric-card {
                            background: var(--bg-card);
                            border: 1px solid var(--border-color);
                            border-radius: 16px;
                            padding: 1.5rem;
                            transition: transform 0.2s, border-color 0.2s;
                        }
                        .metric-card:hover {
                            transform: translateY(-3px);
                            border-color: var(--accent-blue);
                        }
                        .metric-label {
                            font-size: 0.8rem;
                            text-transform: uppercase;
                            letter-spacing: 0.05em;
                            color: var(--text-muted);
                            margin-bottom: 0.5rem;
                        }
                        .metric-val {
                            font-size: 1.4rem;
                            font-weight: 700;
                            color: #fff;
                        }
                        .metric-val.green { color: #34d399; }
                        .section-card {
                            background: var(--bg-card);
                            border: 1px solid var(--border-color);
                            border-radius: 20px;
                            padding: 2rem;
                            margin-bottom: 2rem;
                            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
                        }
                        .section-title {
                            font-size: 1.25rem;
                            font-weight: 700;
                            margin-bottom: 1.5rem;
                            display: flex;
                            align-items: center;
                            gap: 10px;
                        }
                        .pipeline-steps {
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(130px, 1fr));
                            gap: 12px;
                        }
                        .step-item {
                            background: rgba(255, 255, 255, 0.03);
                            border: 1px solid rgba(16, 185, 129, 0.3);
                            border-radius: 12px;
                            padding: 14px 10px;
                            text-align: center;
                            position: relative;
                        }
                        .step-num {
                            font-size: 0.75rem;
                            font-weight: 700;
                            color: #10b981;
                            text-transform: uppercase;
                            margin-bottom: 4px;
                        }
                        .step-name {
                            font-size: 0.9rem;
                            font-weight: 600;
                        }
                        .step-status {
                            display: inline-block;
                            margin-top: 8px;
                            font-size: 0.75rem;
                            padding: 3px 8px;
                            background: rgba(16, 185, 129, 0.2);
                            color: #34d399;
                            border-radius: 6px;
                        }
                        .tech-badges {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 10px;
                            margin-top: 1rem;
                        }
                        .tech-badge {
                            background: rgba(255, 255, 255, 0.05);
                            border: 1px solid var(--border-color);
                            padding: 8px 16px;
                            border-radius: 8px;
                            font-size: 0.85rem;
                            font-weight: 500;
                        }
                        .action-box {
                            display: flex;
                            gap: 14px;
                            margin-top: 1.5rem;
                            flex-wrap: wrap;
                        }
                        .btn {
                            padding: 12px 22px;
                            border-radius: 10px;
                            font-weight: 600;
                            font-size: 0.95rem;
                            text-decoration: none;
                            display: inline-flex;
                            align-items: center;
                            gap: 8px;
                            cursor: pointer;
                            border: none;
                            transition: opacity 0.2s, transform 0.2s;
                        }
                        .btn:hover { opacity: 0.9; transform: translateY(-1px); }
                        .btn-primary {
                            background: linear-gradient(135deg, var(--accent-blue), #2563eb);
                            color: #fff;
                        }
                        .btn-secondary {
                            background: rgba(255, 255, 255, 0.08);
                            color: #fff;
                            border: 1px solid var(--border-color);
                        }
                        #api-response {
                            margin-top: 1rem;
                            background: #05080f;
                            border: 1px solid var(--border-color);
                            border-radius: 10px;
                            padding: 1rem;
                            font-family: monospace;
                            font-size: 0.85rem;
                            color: #38bdf8;
                            display: none;
                        }
                        .footer {
                            text-align: center;
                            font-size: 0.85rem;
                            color: var(--text-muted);
                            margin-top: 1rem;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <header class="header">
                            <div class="logo-wrap">
                                <div class="logo-icon">&#9881;</div>
                                <div>
                                    <h2 style="font-size: 1.2rem; font-weight: 700;">DevOps CI/CD Hub</h2>
                                    <p style="font-size: 0.8rem; color: var(--text-muted);">Spring Boot &bull; Gradle &bull; Docker &bull; Jenkins</p>
                                </div>
                            </div>
                            <div class="badge">
                                <div class="dot"></div>
                                Container Status: %s
                            </div>
                        </header>

                        <section class="hero">
                            <h1>%s</h1>
                            <p>%s</p>
                            <div class="action-box" style="justify-content: center;">
                                <button class="btn btn-primary" onclick="testHealth()">Ping /api/status</button>
                                <a class="btn btn-secondary" href="http://localhost:8080" target="_blank">Open Jenkins :8080 &rarr;</a>
                                <a class="btn btn-secondary" href="/api/status" target="_blank">Raw JSON Endpoint &rarr;</a>
                            </div>
                            <pre id="api-response"></pre>
                        </section>

                        <div class="metrics-grid">
                            <div class="metric-card">
                                <div class="metric-label">Application Status</div>
                                <div class="metric-val green">%s</div>
                            </div>
                            <div class="metric-card">
                                <div class="metric-label">Deployment Port</div>
                                <div class="metric-val">8081 &rarr; 8080</div>
                            </div>
                            <div class="metric-card">
                                <div class="metric-label">Docker Container</div>
                                <div class="metric-val">devops-demo</div>
                            </div>
                            <div class="metric-card">
                                <div class="metric-label">Base Runtime</div>
                                <div class="metric-val">Java 17 JRE</div>
                            </div>
                        </div>

                        <div class="section-card">
                            <div class="section-title">&#9889; Automated 6-Stage CI/CD Pipeline</div>
                            <div class="pipeline-steps">
                                <div class="step-item">
                                    <div class="step-num">Stage 1</div>
                                    <div class="step-name">Checkout</div>
                                    <div class="step-status">&#10003; Git SCM</div>
                                </div>
                                <div class="step-item">
                                    <div class="step-num">Stage 2</div>
                                    <div class="step-name">Build</div>
                                    <div class="step-status">&#10003; Gradle</div>
                                </div>
                                <div class="step-item">
                                    <div class="step-num">Stage 3</div>
                                    <div class="step-name">Testing</div>
                                    <div class="step-status">&#10003; JUnit 5</div>
                                </div>
                                <div class="step-item">
                                    <div class="step-num">Stage 4</div>
                                    <div class="step-name">Docker Build</div>
                                    <div class="step-status">&#10003; Image</div>
                                </div>
                                <div class="step-item">
                                    <div class="step-num">Stage 5</div>
                                    <div class="step-name">Deployment</div>
                                    <div class="step-status">&#10003; Container</div>
                                </div>
                                <div class="step-item">
                                    <div class="step-num">Stage 6</div>
                                    <div class="step-name">Health Check</div>
                                    <div class="step-status">&#10003; Verified</div>
                                </div>
                            </div>
                        </div>

                        <div class="section-card">
                            <div class="section-title">&#128736; Technology Stack</div>
                            <div class="tech-badges">
                                <span class="tech-badge">&#9749; Java 17 LTS</span>
                                <span class="tech-badge">&#127793; Spring Boot 3.2.5</span>
                                <span class="tech-badge">&#128024; Gradle 8.7 Wrapper</span>
                                <span class="tech-badge">&#9989; JUnit 5 Jupiter</span>
                                <span class="tech-badge">&#128051; Docker Engine</span>
                                <span class="tech-badge">&#128104;&#8205;&#128187; Jenkins CI/CD</span>
                            </div>
                        </div>

                        <footer class="footer">
                            Automated CI/CD Pipeline Mini-Project &bull; Zero-Downtime Container Deployment
                        </footer>
                    </div>

                    <script>
                        function testHealth() {
                            const box = document.getElementById('api-response');
                            box.style.display = 'block';
                            box.innerText = 'Querying GET /api/status ...';
                            fetch('/api/status')
                                .then(res => res.json())
                                .then(data => {
                                    box.innerText = 'HTTP 200 OK\\n' + JSON.stringify(data, null, 2);
                                })
                                .catch(err => {
                                    box.innerText = 'Error: ' + err;
                                });
                        }
                    </script>
                </body>
                </html>
                """
                .formatted(appName, status, appName, welcomeMsg, status);
    }

    @GetMapping(value = "/api/info")
    public Map<String, String> homeJson() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("application", statusService.getApplicationName());
        response.put("message", statusService.getWelcomeMessage());
        return response;
    }

    @GetMapping("/api/status")
    public Map<String, String> getStatus() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("application", statusService.getApplicationName());
        response.put("status", statusService.getStatus());
        return response;
    }
}
