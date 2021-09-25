$('.owl-carousel').owlCarousel({
    loop:true,
    margin:0,
    autoplay:true,
    autoplayTimeout:2000,
    stagePadding:-1,
    autoplayHoverPause:true,
    responsiveClass:true,
    responsive:{
        0:{
            items:1,
            nav:true
        },
        600:{
            items:2,
        },
        1000:{
            items:1,
        }
    }
})
$(document).ready(function(){
	let now = new Date();
	var date = moment(now).format('YYYY-MM-DDTHH:MM');
	//console.log(date);
	$("#start_time").val(date);
	$("#end_time").val(date);
});

$(function(){
   $('.countdown').each(function() {
      var $this = $(this), finalDate = $(this).data('countdown');
      $this.countdown(finalDate, function(event) {
         $this.html(event.strftime('%D days %H:%M:%S hrs'))}).on('finish.countdown', function() {
            alert("Finish"); 
          });
     });
 });
