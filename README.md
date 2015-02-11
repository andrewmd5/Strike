# Strike

[![](http://img.youtube.com/vi/ZrRkTYSCs-M/0.jpg)](https://www.youtube.com/watch?v=ZrRkTYSCs-M)

# TODO

- [x] Create one universal style/theme. Currently a bit thrown together
- [x] Do away with cheap hacks on the backend
- [ ] Add torrent searching
- [ ] Add real security measures
- [ ] Web server integration
- [ ] Deluge auto extractor

#### Welcome to Strike, my awful attempts at creating a reliable media management platform. There is a lot of bad code and even more bugs, but this short guide will teach you how to go about setting up this wonderful project. Lets get started.



### Download required software 

Strike works with MPC-HD and any torrent client that supports a Web UI (Tested with Deluge)



Now head to https://github.com/Codeusa/Strike/releases 

Download the latest version of StrikeWeb (Currently 4.0) 

Download the android application from https://play.google.com/store/apps/details?id=net.codeusa.strike

You can download MPC from http://mpc-hc.org/

If you don't have a torrent client with a Web Interface, download Deluge from http://deluge-torrent.org/

### Configuring MPC

Next lets configure MPC for Strike. 

Create a folder in your C drive called "web", place the files from the WebStrike zip you downloaded inside it, it should look like this
![](http://i.imgur.com/C2Rzb8T.png)


Now open Media Player Classic

Go to View -> Options -> Player -> Web Interface and make sure your settings look similar to this

![](http://i.imgur.com/cGiAMBi.png)

Click browse and select the folder you created earlier

Now the next part is not currently configurable unless you edit the Web Strike source, which you're free to do. By default it looks for media at C:\Media\ and it will automatically build file chains of any folders in there. Filtering out non media files. So organize your content.

You can change this by editing the the links to browser.html

NOTE: Whatever port you bind MPC, make sure its forwarded on your router so you can access it remotely.

### Configuring Deluge

As of now I currently use Deluge, so that is the client I'll be supporting here, google if your torrent client supports a Web UI and follow a setup guide for that if you do not use deluge.

Open deluge and click Edit -> Preferences -> Plugins

Make sure it looks like this

![](http://i.imgur.com/pJi9zn2.png)

Then click WebUI and ensure it looks similar to this, the port is whatever you want it to be. Just make sure its forwarded on your router.

![](http://i.imgur.com/xS0gdrR.png)


Click apply and you're done. When you visit the Deluge Web UI for the first time it will prompt you for a password/ask you to change it. By default the password is  "deluge"

### Configuring Strike Android

Assuming you've downloaded the app from the play store, follow the steps accordingly.

Start the application, click the Setting button on the side bar and begin configuring your servers.

If you are unsure what to put here skip down to "Configuring Domains" and come back to this. You will not be able to use Strike until this is configured.

![](http://i.imgur.com/d4LreTe.png)

Click save and the app will check if your settings are valid. 
You should get this message
![](http://i.imgur.com/O57BkTI.png)

If you did, Strike by clicking Remote Control on the side panel, if it loads Strike has been configured.

If all is well click Remote on the sidebar and your control panel should load

![](http://i.imgur.com/DGMuoOo.png)

### Configuring Domains

Assuming you own one, you can configure your domaim to point at your Strike bundle. Its pretty simple

In cloudflare add an A record to your domain similar to this (or whatever DNS service you use)

![](http://i.imgur.com/CTng2pk.png)

Where it says "media" this can be anything you want and where it says the ip address, this obviously needs to be YOUR servers IP address.

If you don't own a domain simply get the PUBLIC ip of whatever machine you're running MPC on, to do this, open this link on your media server http://www.whatsmyip.org/ and copy the ip it provides.

If your ports are forwarded for the ports you picked earlier on your router for that machine, you should be able to access it just fine. 

Either by going to 

media.github.com:5353 (replace with your domains/ports)

or

79.25.25.124:port (that ip should be your machines public ip and that port should be the one you forwarded)


### Trouble Shooting 

If your Deluge web panel does not load or your Strike panel from MPC does not load, it could be a few things.

Deluge or MPC are not running
You did not forward the ports you picked
You're pointing your domain to the wrong address
Connections are blocked by your firewall

### Final Notes

This project will be updated to suck less, contribute if you want. Big thanks to http://dmxt.org/portfolio/ for helping with the designs.
