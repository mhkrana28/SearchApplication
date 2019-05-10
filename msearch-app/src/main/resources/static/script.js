$(document).ready(function() {
	
		var table = $("#jobTable");
		var tBody = $("tBody");
		
		

		$(document).on("input", "#search", function() {
			var text = $('#search').val();
			$.get("http://localhost:8080/search/from/job", {
				text : text
			}, function(response) {
				if (response != null) {
					tBody.empty();
					$.each(response, function(key, value) {
						var row = $("<tr><td class=\"smallTd\"></td><td class=\"bigTd\"></td><td class=\"midTd\"></td></tr>");
						row.children().eq(0).text(value['id']);
						row.children().eq(1).text(value['title']);
						row.children().eq(2).text(value['location']);
						row.appendTo(tBody);
					});//Loop ends
				} else {
					tBody.empty();
				}
			});
		});
		
		
		
	});