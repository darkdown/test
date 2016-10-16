// auto clear song in baidu play list that played 
var playRowId = - 1;
clearInterval(loopId);
var loopId = setInterval(function () {
  var rowId = $('.ui-reelList-active').attr('reellist-row');
  if (playRowId != rowId) {
    console.info('clear -> ' + playRowId);
    $('[reellist-row="' + playRowId + '"]').find('.ui-reelList-checkbox').click();
    if ($('[reellist-row="' + playRowId + '"]') && $('[reellist-row="' + playRowId + '"]').attr('class')
        && $('[reellist-row="' + playRowId + '"]').attr('class').contains('ui-reelList-checked')) {
      $('[#myFooter .delete-button').click();
    } else {
      console.info('miss delete : ' + $(".ui-reelList-checked").length);
    }
    playRowId = $('.ui-reelList-active').attr('reellist-row');
    console.info('update -> ' + playRowId + ' => ' + $('.ui-reelList-active').find('.songname-txt').text());
  }
  var totalTimes = $('#timeWrap .totalTime').text().split(':');
  var totalTime = parseInt(totalTimes[0]) * 60 + parseInt(totalTimes[1]);
  var curTimes = $('#timeWrap .curTime').text().split(':');
  var curTime = parseInt(curTimes[0]) * 60 + parseInt(curTimes[1]);
  if (1 > $('#timeWrap .perTime').length || 1 > $('#timeWrap .perTime_br').length) {
    $('#timeWrap .perTime_br').remove();
    $('#timeWrap .perTime').remove();
    $('<span class="perTime"></span><br class="perTime_br"/>').insertBefore('#timeWrap .curTime');
  }
  var perTime = curTime / totalTime * 100;
  $('#timeWrap .perTime').text(perTime.toFixed(2) + '%');
}, 1000);
