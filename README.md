# Strike 

Strike is a multiplatform MPC web wrapper that hopes to allow for easier management of media, remotely. 

# TODO

- [x] Create one universal style/theme. Currently a bit thrown together
- [x] Do away with cheap hacks on the backend
- [ ] Add torrent searching
- [ ] Add real security measures
- [ ] Web server integration
- [ ] Deluge auto extractor

# Setup Guide

Setting up Strike can seem kind of confusing for novice users, but I'm here to walk you through the process and get you on your way to a better home media server.

#### Downloads

So for Strike to work properly you'll need to download a few things.

* [MPC] - Media Player Classic
* [StrikeWeb] - The wrapper that fuses with SVP
* [AndroidApp] - The android app


#### Setting up Strike Web

Once you've downloaded the latest version of Strike Web, create a new directory like so

![](http://i.imgur.com/w4sLlzO.gif)

Extract the contents of the StrikeWeb zip into that directory, the end results should look like this

![](http://i.imgur.com/OgSGohI.png)

#### Setting up MPC

Once you've installed MPC click **View -> Options** when the new menu opens click on **Web Interface** 

The first thing you should do is check the box that says ** Listen on Port** after this you are free to change the port number from ** 13579** to whatever you'd like if you so desire.

Follow the steps taken in the below gif, making sure to browse for the folder you created earlier

![](http://i.imgur.com/HT9KsAf.gif)

Once you've done this open up the following in your web browser

http://localhost:13579  (substitute 13579 with whatever port you chose)

If it loads, Strike Web is working

#### Setting up a torrent client
If you torrent client supports a web interface, configure it and set it up using the recommeneded steps for that client.

#### Forward your ports
Forward the ports for both MPC and your torrent client within your router, if you are unsure how to do this, follow this guide.

https://www.youtube.com/watch?v=ye_BnHAEJGU

Once forwarded try accessing Strike Web via your machines public IP.

***I am creating a method to skip this if you find yourself unable to forward, it will be released soon***

#### Setting up the android app

Once you've downloaded the app from the Play Store, go to the settings menu and enter the IP/ports of your MPC/Torrent client using the given example, if you have your own domain you can use that too.

http://ip:port //replace "ip" with your ip & "port" with your port

http://domain:port // replace "domain" with your domain/subdomain and "port" with your port

![](http://i.imgur.com/MbpSOAc.png)

If your ports are forwarded the android app should inform you your settings are fine, else it will throw it an error if it can't connect to either client.

#### Orginizaing your media

By default Strike looks for media in C:/Media/, you can set this path in the settings if you wish to change it. 

#### Trouble shooting

If your Strike panel from MPC does not load, it could be a few things.

MPC is not running, you did not forward the ports you picked, you're pointing your domain to the wrong address, connections are blocked by your firewall. 

#### Contact

If you have any issues contact me on my [blog]


**Free Software, Hell Yeah!**

[MPC]:http://mpc-hc.org/
[Deluge]:http://deluge-torrent.org/
[StrikeWeb]:https://github.com/Codeusa/Strike/releases
[AndroidApp]:https://play.google.com/store/apps/details?id=net.codeusa.strike&hl=en
[blog]:http://blog.andrew.im/ask/
