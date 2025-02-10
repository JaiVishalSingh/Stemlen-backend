package com.stemlen.utility;

public class OTPTemp {
    public static String getMessageBody(String otp, String name) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>OTP Verification</title>
                    <style>
                        body {
                            font-family: 'Nunito Sans', sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .email-container {
                            max-width: 600px;
                            margin: 40px auto;
                            background-color: #ffffff;
                            border: 1px solid #e0e0e0;
                            border-radius: 8px;
                            overflow: hidden;
                            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                        }
                        .email-header {
                            background-color: #0056b3;
                            color: #ffffff;
                            padding: 20px;
                            text-align: center;
                            font-size: 22px;
                            font-weight: 600;
                        }
                        .email-body {
                            padding: 30px;
                            color: #333333;
                            font-size: 16px;
                            line-height: 1.6;
                        }
                        .email-body p {
                            margin: 0 0 15px;
                        }
                        .otp {
                            display: inline-block;
                            margin: 20px 0;
                            padding: 10px 20px;
                            font-size: 28px;
                            font-weight: bold;
                            color: #0056b3;
                            background-color: #f0f4ff;
                            border: 1px solid #d0e2ff;
                            border-radius: 4px;
                            letter-spacing: 2px;
                        }
                        .email-footer {
                            background-color: #f9f9f9;
                            padding: 20px;
                            text-align: center;
                            font-size: 14px;
                            color: #888888;
                            border-top: 1px solid #e0e0e0;
                        }
                        .email-footer a {
                            color: #0056b3;
                            text-decoration: none;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="email-header">Your One-Time Password (OTP)</div>
                        <div class="email-body">
                            <p>Dear %s,</p>
                            <p>We have received a request to verify your email address. Please use the OTP below to proceed:</p>
                            <div class="otp">%s</div>
                            <p>This OTP is valid for the next 10 minutes. For your security, do not share it with anyone.</p>
                            <p>If you did not request this, please disregard this email or contact our support team immediately.</p>
                        </div>
                        <div class="email-footer">
                            <p>Thank you,<br>Your Company Team</p>
                            <p><a href="https://www.yourcompany.com/support">Contact Support</a></p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(name, otp);
    }
}
