(function () {
    var _1 = 20;
    var _2 = 0;
    var _3 = null;
    var _4 = null;
    var _5 = 0;
    var _6 = location.hash;
    var _7 = "#_";
    var _8 = [];
    var _9 = 0;
    var _a;
    window.iui = {
        showPage: function (_b, _c) {
            if (_b) {
                if (_4) {
                    _4.removeAttribute("selected");
                    _4 = null;
                }
                if (hasClass(_b, "dialog")) {
                    showDialog(_b);
                } else {
                    var _d = _3;
                    _3 = _b;
                    if (_d) {
                        setTimeout(slidePages, 0, _d, _b, _c);
                    } else {
                        updatePage(_b, _d);
                    }
                }
            }
        }, showPageById: function (_e) {
            var _f = $(_e);
            if (_f) {
                var _10 = _8.indexOf(_e);
                var _11 = _10 != -1;
                if (_11) {
                    _8.splice(_10, _8.length);
                }
                iui.showPage(_f, _11);
            }
        }, showPageByHref: function (_12, _13, _14, _15, cb) {
            var req = new XMLHttpRequest();
            req.onerror = function () {
                if (cb) {
                    cb(false);
                }
            };
            req.onreadystatechange = function () {
                if (req.readyState == 4) {
                    if (_15) {
                        replaceElementWithSource(_15, req.responseText);
                    } else {
                        var _18 = document.createElement("div");
                        _18.innerHTML = req.responseText;
                        iui.insertPages(_18.childNodes);
                    }
                    if (cb) {
                        setTimeout(cb, 1000, true);
                    }
                }
            };
            if (_13) {
                req.open(_14 || "GET", _12, true);
                req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                req.setRequestHeader("Content-Length", _13.length);
                req.send(_13.join("&"));
            } else {
                req.open(_14 || "GET", _12, true);
                req.send(null);
            }
        }, insertPages: function (_19) {
            var _1a;
            for (var i = 0; i < _19.length; ++i) {
                var _1c = _19[i];
                if (_1c.nodeType == 1) {
                    if (!_1c.id) {
                        _1c.id = "__" + (++_9) + "__";
                    }
                    var _1d = $(_1c.id);
                    if (_1d) {
                        _1d.parentNode.replaceChild(_1c, _1d);
                    } else {
                        document.body.appendChild(_1c);
                    }
                    if (_1c.getAttribute("selected") == "true" || !_1a) {
                        _1a = _1c;
                    }
                    --i;
                }
            }
            if (_1a) {
                iui.showPage(_1a);
            }
        }, getSelectedPage: function () {
            for (var _1e = document.body.firstChild; _1e; _1e = _1e.nextSibling) {
                if (_1e.nodeType == 1 && _1e.getAttribute("selected") == "true") {
                    return _1e;
                }
            }
        }
    };
    addEventListener("load", function (_1f) {
        var _20 = iui.getSelectedPage();
        if (_20) {
            iui.showPage(_20);
        }
        setTimeout(preloadImages, 0);
        setTimeout(checkOrientAndLocation, 0);
        _a = setInterval(checkOrientAndLocation, 300);
    }, false);
    addEventListener("click", function (_21) {
        var _22 = findParent(_21.target, "a");
        if (_22) {
            function unselect() {
                _22.removeAttribute("selected");
            }

            if (_22.href && _22.hash && _22.hash != "#") {
                _22.setAttribute("selected", "true");
                iui.showPage($(_22.hash.substr(1)));
                setTimeout(unselect, 500);
            } else {
                if (_22 == $("backButton")) {
                    history.back();
                } else {
                    if (_22.getAttribute("type") == "submit") {
                        submitForm(findParent(_22, "form"));
                    } else {
                        if (_22.getAttribute("type") == "cancel") {
                            cancelDialog(findParent(_22, "form"));
                        } else {
                            if (_22.target == "_replace") {
                                _22.setAttribute("selected", "progress");
                                iui.showPageByHref(_22.href, null, null, _22, unselect);
                            } else {
                                if (!_22.target) {
                                    _22.setAttribute("selected", "progress");
                                    iui.showPageByHref(_22.href, null, null, null, unselect);
                                } else {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            _21.preventDefault();
        }
    }, true);
    addEventListener("click", function (_23) {
        var div = findParent(_23.target, "div");
        if (div && hasClass(div, "toggle")) {
            div.setAttribute("toggled", div.getAttribute("toggled") != "true");
            _23.preventDefault();
        }
    }, true);
    function checkOrientAndLocation() {
        if (window.innerWidth != _5) {
            _5 = window.innerWidth;
            var _25 = _5 == 320 ? "profile" : "landscape";
            document.body.setAttribute("orient", _25);
            setTimeout(scrollTo, 100, 0, 1);
        }
        if (location.hash != _6) {
            var _26 = location.hash.substr(_7.length);
            iui.showPageById(_26);
        }
    }

    function showDialog(_27) {
        _4 = _27;
        _27.setAttribute("selected", "true");
        if (hasClass(_27, "dialog") && !_27.target) {
            showForm(_27);
        }
    }

    function showForm(_28) {
        _28.onsubmit = function (_29) {
            _29.preventDefault();
            submitForm(_28);
        };
        _28.onclick = function (_2a) {
            if (_2a.target == _28 && hasClass(_28, "dialog")) {
                cancelDialog(_28);
            }
        };
    }

    function cancelDialog(_2b) {
        _2b.removeAttribute("selected");
    }

    function updatePage(_2c, _2d) {
        if (!_2c.id) {
            _2c.id = "__" + (++_9) + "__";
        }
        location.href = _6 = _7 + _2c.id;
        _8.push(_2c.id);
        var _2e = $("pageTitle");
        if (_2c.title) {
            _2e.innerHTML = _2c.title;
        }
        if (_2c.localName.toLowerCase() == "form" && !_2c.target) {
            showForm(_2c);
        }
        var _2f = $("backButton");
        if (_2f) {
            var _30 = $(_8[_8.length - 2]);
            if (_30 && !_2c.getAttribute("hideBackButton")) {
                _2f.style.display = "inline";
                _2f.innerHTML = _30.title ? _30.title : "Back";
            } else {
                _2f.style.display = "none";
            }
        }
    }

    function slidePages(_31, _32, _33) {
        var _34 = (_33 ? _31 : _32).getAttribute("axis");
        if (_34 == "y") {
            (_33 ? _31 : _32).style.top = "100%";
        } else {
            _32.style.left = "100%";
        }
        _32.setAttribute("selected", "true");
        scrollTo(0, 1);
        clearInterval(_a);
        var _35 = 100;
        slide();
        var _36 = setInterval(slide, _2);

        function slide() {
            _35 -= _1;
            if (_35 <= 0) {
                _35 = 0;
                if (!hasClass(_32, "dialog")) {
                    _31.removeAttribute("selected");
                }
                clearInterval(_36);
                _a = setInterval(checkOrientAndLocation, 300);
                setTimeout(updatePage, 0, _32, _31);
            }
            if (_34 == "y") {
                _33 ? _31.style.top = (100 - _35) + "%" : _32.style.top = _35 + "%";
            } else {
                _31.style.left = (_33 ? (100 - _35) : (_35 - 100)) + "%";
                _32.style.left = (_33 ? -_35 : _35) + "%";
            }
        }
    }

    function preloadImages() {
        var _37 = document.createElement("div");
        _37.id = "preloader";
        document.body.appendChild(_37);
    }

    function submitForm(_38) {
        iui.showPageByHref(_38.action || "POST", encodeForm(_38), _38.method);
    }

    function encodeForm(_39) {
        function encode(_3a) {
            for (var i = 0; i < _3a.length; ++i) {
                if (_3a[i].name) {
                    args.push(_3a[i].name + "=" + escape(_3a[i].value));
                }
            }
        }

        var _3c = [];
        encode(_39.getElementsByTagName("input"));
        encode(_39.getElementsByTagName("select"));
        return _3c;
    }

    function findParent(_3d, _3e) {
        while (_3d && (_3d.nodeType != 1 || _3d.localName.toLowerCase() != _3e)) {
            _3d = _3d.parentNode;
        }
        return _3d;
    }

    function hasClass(_3f, _40) {
        var re = new RegExp("(^|\\s)" + _40 + "($|\\s)");
        return re.exec(_3f.getAttribute("class")) != null;
    }

    function replaceElementWithSource(_42, _43) {
        var _44 = _42.parentNode;
        var _45 = _42;
        while (_44.parentNode != document.body) {
            _44 = _44.parentNode;
            _45 = _45.parentNode;
        }
        var _46 = document.createElement(_45.localName);
        _46.innerHTML = _43;
        _44.removeChild(_45);
        while (_46.firstChild) {
            _44.appendChild(_46.firstChild);
        }
    }

    function $(id) {
        return document.getElementById(id);
    }

    function ddd() {
        console.log.apply(console, arguments);
    }
})();