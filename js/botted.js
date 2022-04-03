jQuery(document).ready(function($) {

	var $username;
    jQuery('.submit').on('input', function() {
        $username = $( "uname" ).val();
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