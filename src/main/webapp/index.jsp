<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Web Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        h1, h2 {
            color: #333;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        ul li {
            margin: 10px 0;
        }

        a {
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        footer {
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #ccc;
            font-size: 0.8em;
            text-align: center;
        }
    </style>
</head>
<body>

<h1>Welcome to My Web Application!</h1>
<p>This is a simple JSP page designed to demonstrate basic navigation within a web application.</p>

<h2>Navigation</h2>
<ul>
    <li><a href="/users/">Manage Users</a></li>
    <li><a href="/posts/">Manage Posts</a></li>
</ul>

<footer>
    <p>&copy; 2024 Your Name or Company Name</p>
</footer>

</body>
</html>
