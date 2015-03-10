
window.isValidURL = (function () {// wrapped in self calling function to prevent global pollution

    //URL pattern based on rfc1738 and rfc3986
    var rg_pctEncoded = "%[0-9a-fA-F]{2}";
    var rg_protocol = "(http|https):\\/\\/";

    var rg_userinfo = "([a-zA-Z0-9$\\-_.+!*'(),;:&=]|" + rg_pctEncoded + ")+" + "@";

    var rg_decOctet = "(25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|[1-9][0-9]|[0-9])"; // 0-255
    var rg_ipv4address = "(" + rg_decOctet + "(\\." + rg_decOctet + "){3}" + ")";
    var rg_hostname = "([a-zA-Z0-9\\-\\u00C0-\\u017F]+\\.)+([a-zA-Z]{2,})";
    var rg_port = "[0-9]+";

    var rg_hostport = "(" + rg_ipv4address + "|localhost|" + rg_hostname + ")(:" + rg_port + ")?";

    // chars sets
    // safe           = "$" | "-" | "_" | "." | "+"
    // extra          = "!" | "*" | "'" | "(" | ")" | ","
    // hsegment       = *[ alpha | digit | safe | extra | ";" | ":" | "@" | "&" | "=" | escape ]
    var rg_pchar = "a-zA-Z0-9$\\-_.+!*'(),;:@&=";
    var rg_segment = "([" + rg_pchar + "]|" + rg_pctEncoded + ")*";

    var rg_path = rg_segment + "(\\/" + rg_segment + ")*";
    var rg_query = "\\?" + "([" + rg_pchar + "/?]|" + rg_pctEncoded + ")*";
    var rg_fragment = "\\#" + "([" + rg_pchar + "/?]|" + rg_pctEncoded + ")*";

    var rgHttpUrl = new RegExp(
        "^"
        + rg_protocol
        + "(" + rg_userinfo + ")?"
        + rg_hostport
        + "(\\/"
        + "(" + rg_path + ")?"
        + "(" + rg_query + ")?"
        + "(" + rg_fragment + ")?"
        + ")?"
        + "$"
    );

    // export public function
    return function (url) {
        if (rgHttpUrl.test(url)) {
            return true;
        } else {
            return false;
        }
    };
})();

var errorMessage = "";


function getValue(id) {
    return document.getElementById(id)
        .value;
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}


function checkMPC(mpcserver) {
	if (isBlank(mpcserver)) {
        BootstrapDialog.alert('Please enter a host for MPC');
        return false;
    }
	if (!window.isValidURL(mpcserver)) {
        BootstrapDialog.alert('Please enter a valid URL for MPC');
        return false;
    }
	return true;
	
}

function checkTorrent(delugeserver) {
	if (isBlank(delugeserver)) {
        return false;
    }
	if (!window.isValidURL(delugeserver)) {
        BootstrapDialog.alert('Please enter a valid URL for Deluge');
        return false;
    }
	return true;
}
function save(clicked) { //code smell 
    var mpcserver = getValue("mpc");
    var delugeserver = getValue("deluge");
	var browserpath = getValue("browser");
	  var errors = false;
	  var skipTorrent = false;
	if (!checkMPC(mpcserver)) {
		return false;
	}
	if (!checkTorrent(delugeserver)) {
		skipTorrent = true;
	}
   var mpcStatus = window.strike.checkStatus(mpcserver);
   if (mpcStatus.indexOf("false") > -1) {
        errors = true;
        sendError(1);
    }
   if (!skipTorrent) {
    var torrentStatus = window.strike.checkStatus(delugeserver);
    
    if (torrentStatus.indexOf("false") > -1) {
        errors = true;
        sendError(2);
    }
   }
   
	if (isBlank(browserpath)) {
		  BootstrapDialog.alert('Please enter a path for the browser to default to');
		  errors = true;
        return false;
    }
    if (errors === true) {
        return false;
    }
	
    localStorage.mpc = btoa(mpcserver);
	if (isBlank(delugeserver)) {
        localStorage.deluge = btoa("notset");
    } else {
    localStorage.deluge = btoa(delugeserver);
	}
	localStorage.browser = btoa(browserpath);
	if (clicked === true) {
		 location.reload();
	} else {
		BootstrapDialog.alert('Your settings are valid.');
		window.strike.saveSettings(mpcserver + ',' + delugeserver + "," + browserpath);

	}
   
}

function sendError(type) {

    $("#error" + type).fadeIn("slow", function () {
        // Animation complete
    });
}


$("#save").click(function (e) {

    save(true);
});


function load() {
    var errors = false;

    var mpcurl = atob(localStorage.mpc);
    $('#mpc').val(mpcurl);
    var delugeurl = atob(localStorage.deluge);
	if (delugeurl === "notset") {
		delugeurl = "";
	}
    $('#deluge').val(delugeurl);
	 var browserpath = atob(localStorage.browser);
    $('#browser').val(browserpath);
    save(false);

}

load();