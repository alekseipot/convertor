<html>
<head>
    <script type="text/javascript" src="js/jquery/jquery-2.1.3.js"></script>

    <script>
        function sendRequest() {
            var path = $("#source-file-form").attr("action") + $("#requestedPage").val().trim();
            $("#source-file-form").attr("action", path);
            $("#source-file-form").submit(); //Submit the form
        }
    </script>
</head>
<body>
<h1>Upload your file</h1>

<form id="source-file-form" action="/convertor/engine/convert/upload/" method="post" enctype="multipart/form-data">

    <p>
        Select a file : <input type="file" name="uploadedFile"/>
    </p>

    <p>
        Input page number : <input type="text" id="requestedPage" name="requestedPage"/>
    </p>

    <input type="button" onclick="return sendRequest();" value="Convert It"/>

</form>
</body>
</html>
