var express = require('express')
var http = require('http')
var app = express()
var  sqlite3 = require('sqlite3').verbose()
var db = new sqlite3.Database(':information:');
db.serialize(function(){
	db.run('CREATE TABLE information (email text, subject text, course text,section text)')
	db.close();
});
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
			db.serialize(function(){
				db.run("INSERT INTO information (email,subject,course,section)")
				db.close()
				
			})
			res.send(str)
		});

	};

	//http.request(options,callback).end();
});

app.listen(3000, function () {
	  console.log('Example app listening on port 3000!')
})
