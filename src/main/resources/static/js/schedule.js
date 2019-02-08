$(function () {
    var id_date = {
        0: "2018-11-09",
        1: "2018-11-10"
    };

    $("#schedule-select").on('click', '.dropdown-menu a', function () {
        $("#schedule-select .btn:first-child").text($(this).data("btn-text"));

        $.get(
            $("#schedule-select").data("rest"), {
                id: $("#schedule-select").data("page-id"),
                date: id_date[this.id]
            },
            renderSessions
        );

    });

    function renderSessions(data) {
        //console.log(JSON.stringify(data, null, 4));

        var set = $("[data-sub-id] #sessions");
        set.each(function (i) {
            $(this).delay(i * 50).animate({opacity: "hide"}, 400, function () {
                if (i === set.length - 1) renderAndShowSessions(data);
            });
        });
    }

    function renderAndShowSessions(data) {
        for (var theater_id in data) {
            //console.log(data[theater_id]);
            var theater_sessions = $('*[data-sub-id="' + theater_id + '"] #sessions');
            theater_sessions.empty();
            var sessions = data[theater_id];

            for (var id in sessions) {
                var datetime = formatDate(new Date(sessions[id].time), "HH:mm");
                var session = sessions[id];

                var session_html =
                    $(`<div class="session" style="display: none;"> \
	                    <div class="session-body"> \
	                        <button type="button" class="btn btn${session.seats.length == 0 ? '-outline' : ''}-primary mr-sm-2 my-1" data-session="${session.id}">${datetime}</button> \
	                        <p class="card-text">от ${session.price} р.</p> \
	                        <p class="card-text">${session.hall.title}</p> \
	                    </div> \
	                </div>`);

                theater_sessions.append(session_html);
            }
        }

        $("[data-sub-id] #sessions").show();
        $("[data-sub-id] #sessions .session").each(function (i) {
            $(this).delay(i * 30).animate({opacity: "show"}, 250);
        });
        bindBookButtons();
    }

    function bindBookButtons() {
        $("#sessions button").click(function () {
            var button = this;
            $.get(
                "/book", {
                    session: $(this).data("session")
                },
                function (data) {
                    if (data == "") {
                        $(button).addClass("btn-outline-primary");
                        $(button).removeClass("btn-primary");
                    }
                    else {
                        $(button).addClass("btn-primary");
                        $(button).removeClass("btn-outline-primary");
                    }
                }
            );
        });
    }

    bindBookButtons();

    var monthNames = [
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    ];
    var dayOfWeekNames = [
        "Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"
    ];

    function formatDate(date, patternStr) {
        if (!patternStr) {
            patternStr = 'dd/mm/yyyy';
        }
        var day = date.getDate(),
            month = date.getMonth(),
            year = date.getFullYear(),
            hour = date.getHours(),
            minute = date.getMinutes(),
            second = date.getSeconds(),
            miliseconds = date.getMilliseconds(),
            h = hour % 12,
            hh = twoDigitPad(h),
            HH = twoDigitPad(hour),
            mm = twoDigitPad(minute),
            ss = twoDigitPad(second),
            aaa = hour < 12 ? 'AM' : 'PM',
            EEEE = dayOfWeekNames[date.getDay()],
            EEE = EEEE.substr(0, 3),
            dd = twoDigitPad(day),
            M = month + 1,
            MM = twoDigitPad(M),
            MMMM = monthNames[month],
            MMM = MMMM.substr(0, 3),
            yyyy = year + "",
            yy = yyyy.substr(2, 2)
        ;
        return patternStr
            .replace('hh', hh).replace('h', h)
            .replace('HH', HH).replace('H', hour)
            .replace('mm', mm).replace('m', minute)
            .replace('ss', ss).replace('s', second)
            .replace('S', miliseconds)
            .replace('dd', dd).replace('d', day)
            .replace('MMMM', MMMM).replace('MMM', MMM).replace('MM', MM).replace('M', M)
            .replace('EEEE', EEEE).replace('EEE', EEE)
            .replace('yyyy', yyyy)
            .replace('yy', yy)
            .replace('aaa', aaa)
            ;
    }

    function twoDigitPad(num) {
        return num < 10 ? "0" + num : num;
    }


});