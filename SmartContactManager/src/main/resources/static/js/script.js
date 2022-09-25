console.log("for Testing")

const togglesidebar = ( ) =>{
	if($(".sidebar").is(":visible"))
{
	$(".sidebar").css("display","none");
	$(".content").css("margin-left","0%");
	
}
else{
	$(".sidebar").css("display","block");
	$(".content").css("margin-left","20%");
}
}


const search = () =>{
	
	let query=$("#search-input").val();
			console.log(query)
 
	
	if(query=='')
	{
	$(".search-result").hide();
	}else
	{
		
		
		let url=`http://localhost:8080/search/${query}`;
		
		fetch(url).then((response) => {
			
			
			return response.json();
		}).then( (data) => {
			
			let text =`<div class='list-group'>`;
			data.forEach((contact) =>{
				text+=`<a href='/user/contact/${contact.id}' class='list-group-item  list-group-action'> ${contact.name} </a>`
			});
			text +=`</div>`;
			
			$(".search-result").html(text);
					$(".search-result").show();
		});
		console.log(query)
	}
	
	
	
}


const paymentStart = () =>
{
	let amount=$("#payment_field").val();
	if(amount==''||amount==null||amount==0)
	{
		alert("Amount cannot be empty or 0");
	}
	
	
	$.ajax(
		
		
		{
			url:'/user/create_order',
			data:JSON.stringify({
				amount:amount,info:'order_request'
			}),
			contentType:'application/json',
			type:'POST',
			dateType:'json',
			success:function(response)
			{
				
				console.log(response);	
				
				
				if(response.status=="created"){
					
				}
			},
			error:function(error)
			{
				alert("Something went wrong !!");
			},
			
		}
	)
	
	
	
	
	
	
	
	
	
	console.log("hi"+ " amount"+ amount);
	
};