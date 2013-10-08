$(function() {

  function lastMonday() {
    var lastWeek = new XDate().addWeeks(-1);
    return new XDate().setWeek(lastWeek.getWeek(), lastWeek.getFullYear());
  }

  var fromDate = lastMonday();

  var ratingSymbol = {
    "happy": "H",
    "neutral": "N",
    "sad": "S"
  };


  function renderWeek(fromDate, smilies, row) {
    var startDate = new XDate(fromDate);
     for(i = 0; i < 5; i++) {
        var dateKey = startDate.toString("yyyy-MM-dd");
        var rating = smilies[dateKey];
        var td = $("<td></td>");
        if (rating) {
          td.text(ratingSymbol[rating]);
        }
        row.append(td);
        startDate.addDays(1);
      }
  }


  $.get( "/smilies/" + fromDate.toString("yyyy-MM-dd"), function(data) {
    $.each(data, function(name, smilies) {
      var row = $("<tr/>"), label = $("<td>" + name + "</td>"), startDate = new XDate(fromDate);
      row.append(label);

      renderWeek(startDate, smilies, row);
      startDate.addDays(7);
      row.append("<td/>");
      renderWeek(startDate, smilies, row);

      $(".nikoniko-individuals").append(row);

    });

//    alert("We got: " + JSON.stringify(data));
  });

});