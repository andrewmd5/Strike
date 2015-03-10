$(document)
    .ready(function () {

        var play = $("#buplay_button");
        var pause = $("#pause_button");
        var stop = $("#stop_button");
        var voumeDown = $("#volume_down");
        var volumeUp = $("#volume_up");
        var mute = $("#mute_button");
        var slowDown = $("#slow_down");
        var speedUp = $("#speed_up");
        var fullscreen = $("#fullscreen_button");
        var fullscreenNoRes = $("#full_no_res");

        var playMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.play();
        }
        var pauseMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.pause();
        }
        var stopMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.stop();
        }
        var mediaVolumeUp = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.volumeUp();
        }
        var mediavVolumeDown = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.volumeDown();
        }
        var muteMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.mute();
        }
        var slowDownMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
           window.strike.sendCommand(894);
        }
        var speedUpMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.sendCommand(895);
        }
        var fullscreenMedia = function (e) {
			e.stopPropagation();
			e.preventDefault();
            window.strike.fullscreen();
        }
        play.on('click', playMedia);
        pause.on('click', pauseMedia);
        stop.on('click', stopMedia);
        voumeDown.on('click', mediavVolumeDown);
        volumeUp.on('click', mediaVolumeUp);
        mute.on('click', muteMedia);
        slowDown.on('click', slowDownMedia);
        speedUp.on('click', speedUpMedia);
        fullscreen.on('click', fullscreenMedia);
        fullscreenNoRes.on('click', fullscreenMedia);
		window.strike.startNotfication();
		

});
