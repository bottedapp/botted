jQuery(document).ready(function($) {
	
	var $username;
	 $('.submit').click(function() {
        $username = $( ".uname" ).val();
        query();
    	});

	function query()
    	{
		$.ajax({
				type: 'post',
				url: '/query.php',
				data: {'username': $username},
				cache:false,
				success: function(data)
				{
				    $( ".result" ).html(data);
				}
		});  
	}
});
