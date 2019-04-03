$('.list-work-account').click(function(){
  $(".child-acc").slideToggle("child-acc-open");
});
$('.total-notify').click(function(){
  $(".list-notify").slideToggle("list-notify-open");
})
/// Chart 2
// end chart
$('.datepicker').datepicker({
  format: 'dd/mm/yyyy',
});
$(".arr-btn").click(function(){
  $(this).toggleClass('icon-arrup').toggleClass('icon-arrdown');
  $(this).parent().parent().find(".tg-list").toggleClass('none-list');
});
if(screen.width < 768) {
  if ($('.menu-top-list').children().hasClass("menu-child")) {
    $(".menu-child").parent().addClass("arr-sub");
  }
  $('.arr-sub').click(function(){
    $(this).find('.menu-child').toggle();
  });
  $('.btn-navbar-top').click(function(e){
    e.preventDefault();
    $('body').toggleClass('hidden-body');
    $('.curtain').toggle();
    $('.open-menu').toggle();
  });
  $('.menu-cau-hinh').click(function(e){
    e.preventDefault();
    $('body').toggleClass('hidden-body');
    $('.curtain').toggle();
    $('.open-menu').toggle();
  });
  $('.arr-sub').click(function(){
    $(this).toggleClass('arr-sub-down');
  });
}