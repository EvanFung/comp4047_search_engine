(function () {
    var query = document.getElementById('queryStr');
    if(query != null) {
        var words = query.value.split(' ');
    }
    if (words != null && query != null) {
        for (var i = 0; i < words.length; i++) {
            highlight(words[i]);
        }
    }

    function highlight(text) {
        var partialContent = document.getElementsByClassName("partial-content");
        for (var i = 0; i < partialContent.length; i++) {
            var innerHTML = partialContent[i].innerHTML;
            var index = innerHTML.indexOf(text);
            if (index >= 0) {
                innerHTML = innerHTML.substring(0, index) + "<span class='highlight'>" + innerHTML.substring(index, index + text.length) + "</span>" + innerHTML.substring(index + text.length);
                partialContent[i].innerHTML = innerHTML;
            }
        }
    }
})()