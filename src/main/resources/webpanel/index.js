fetch("/testrequest").then((response) => response.json()).then(data => console.log(data));

fetch("/testpost", 
{
    body: "Hello world!",
    method: "POST"
}).then(response => response.text()).then(data => alert(data));

