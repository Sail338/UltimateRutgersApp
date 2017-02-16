var express = require('express')
var http = require('http')
var app = express()

app.get('/sniper',function(req,res) {
	//create get request based on params from sent requesrt
	//grab the params
	console.log('REQ' + req.params.subject);
	var options = {
		'host':'sis.rutgers.edu/soc',
		'path': '/courses.json?'+'subject='+'198'+'&semester=12017'+'&campus=NB&level=U'
		
	};
	callback = function(response){
		var str = '';
		response.on('data',function(chunk) {
			str += chunk;
		});

		response.on('end',function(chunk) {
			res.send(str)
		});

	};

	//http.request(options,callback).end();
});

app.listen(3000, function () {
	  console.log('Example app listening on port 3000!')
})
