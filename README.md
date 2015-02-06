# Strikehttps://github.com/Codeusa/Strike
A bunch of preconfigured software endpoints for a sweet home media server.

# TODO

- [ ] Create one universal style/theme. Currently a bit thrown together
- [ ] Do away with cheap hacks on the backend
- [ ] Add torrent searching
- [ ] Add real security measures
- [ ] Web server integration
- [ ] Deluge auto extractor

#### Welcome to Strike, my awful attempts at creating a reliable media management platform. There is a lot of bad code and even more bugs, but this short guide will teach you how to go about setting up this wonderful project. Lets get started.



### Download required software 

Strike works with MPC-HD and Deluge, so you'll need to go download both of these. A quick trip to google will show you the way.


Now head to https://github.com/Codeusa/Strike/releases and download the latest release. If you want to use Strike on your phone download the apk, if you want it on your desktop download the JAR, WebStrike also needs to be downloaded. This is what you'll be installing inside MPC.

### Configuring MPC

Next lets configure MPC for Strike. 

Go to View -> Options -> Player -> Web Interface and make sure your settings look similar to this

![](http://i.imgur.com/cGiAMBi.png)

The path which pages serve from is totally up to you. 

After you've set these options, take the StrikeWeb.zip you downloaded and extract its contents into the chosen path you set.

It should look similar to this
![](http://i.imgur.com/r0MzWuT.png)

Now the next part is not currently configurable unless you edit the Web Strike source, which you're free to do. By default it looks for media at C:\Media\ and it will automatically build file chains of any folders in there. Filtering out non media files.

You can change this by editing the browser.html if you so wish. 

NOTE: Whatever port you bind MPC, make sure its forwarded on your router.


### Configuring Deluge

Open deluge and click Edit -> Preferences -> Plugins

Make sure it looks like this

![](http://i.imgur.com/pJi9zn2.png)

Then click WebUI and ensure it looks similar to this, the port is whatever you want it to be. Just make sure its forwarded.

![](http://i.imgur.com/xS0gdrR.png)


Click apply and you're done. When you visit the Deluge Web UI for the first time it will prompt you for a password/ask you to change it. By default the password is  "deluge"


### Configuring Strike Web

In the Strike Web directory you placed earlier go into the js folder and open auth.js in your favorte editor.

In the validate function you can see an array of usernames/passwords. This is where you can add accounts, of course since this is already insecure to begin with you can strip out this functionality entirely.

### Configuring Strike Android

Assuming you've downloaded the APK from the releases.

If your falvor of android gives you this message
![](http://i.imgur.com/FKq8Mpk.png)

Simply do this in your android settings

![](http://i.imgur.com/iQacAgi.png)

Once the app has started click Settings on the sidebar and enter your server information into the fields provided.

If you are unsure what to put here skip down to "Configuring Domains"

![](http://i.imgur.com/iaWfX5x.png)

Click save and the app will check if your settings are valid. Note https support is wonky and redirecting urls are not allowed. The "http://" portion when inputing is required

You should get this message
![](http://i.imgur.com/O57BkTI.png)



If all is well click Remote on the sidebar and your control panel should load

![](http://i.imgur.com/ycnvllC.png)



### Configuring Strike Desktop

When you first launch the desktop version you'll be prompted with the following

![](http://i.imgur.com/7nZvqOO.png)

Enter your information just as you would the android version and click OK

If you did it right you should see your control panel

![](http://i.imgur.com/5W8SplG.png)

### Configuring Domains

Assuming you own one, you can configure your domaim to point at your Strike bundle. Its pretty simple

In cloudflare add an A record to your domain similar to this

![](http://i.imgur.com/CTng2pk.png)

Where it says "media" this can be anything you want and where it says the ip address, this obviously needs to be YOUR servers IP address.

If you don't own a domain simply get the PUBLIC ip of whatever machine you're running MPC on, just google "whats my ip".

If your ports are forwarded for the port you picked earlier on your router for that machine, you should be able to access it just fine. 

Either by going to 

yoursubdomain.yourdomain.com:port

or

79.25.25.124:port (that ip should be your machines public ip and that port should be the one you forwarded)


### Trouble Shooting 

If your Deluge web panel does not load or your Strike panel from MPC does not load, it could be a few things.

Deluge or MPC are not running
You did not forward the ports
You're pointing your domain to the wrong address
Connections are blocked by your firewall

### Final Notes

This project will be updated to suck less, contribute if you want. 






