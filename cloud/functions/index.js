const functions = require('firebase-functions');
const admin=require('firebase-admin')

admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
const list=[]
list.push('Ajay')
list.push('Brande Carnahan')
list.push('Kenton Primo')
list.push('Chae Limon')
list.push('Thu Lantigua')
list.push('Rebecka Heid')
list.push('Dusti Ambriz')
list.push('Ewa Lorenzana')
list.push('Eunice Escarcega')
list.push('Alease Denmark')
list.push('Rubin Muscarella')
list.push('Miranda Pender') 

exports.dml_viewall= functions.https.onRequest((request, response) => {



    console.log('Request Made ');

    admin.database().ref('/domilearn').once('value').then(function(snap){
        
        var data=snap.val()
        

        response.write('<html><title>View Data</title><body>')
        response.write('Current Name is : '+'<b>'+(data.name)+'</b>'); 
        response.write('<br>Other Names are : <br>');

        var list=data.list;
        list.forEach(element => {
                
            
         response.write('<br>'+'<b>'+element+'</b>');
  
      
        });/**/
        response.write('</html></body>')
        response.end();
    });


});

exports.dml_update= functions.https.onRequest((request, response) => {

 
    admin.database().ref('/domilearn').once('value').then(function(snap){

        response.write('<html><title>Update Name</title><body>')
        var data=snap.val();
        var randomIndex=Math.random(0,data.list.size())
        response.write('Current Name was : '+'<b>'+JSON.stringify(data.name)+'</b>');
        response.write('<br>Set New Name to : '+'<b>'+JSON.stringify(data.list[randomIndex])+'</b>');
        admin.database().ref('/domilearn/name').set(data.list[randomIndex])
        response.write('</html></body>')
        response.end()

    })


});

exports.dml_reset= functions.https.onRequest((request, response) => {

  var re={
      name:list[0],
      list:list
  }
  admin.database().ref('/domilearn').set(re);

  var send={
      status:"Reset Success",
      data:re
  }
  response.send(send);


});