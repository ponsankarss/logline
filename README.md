SupportGuy - Avoid grunt work
=======
http://vijayrc.com/vectorclocks/vrc/post/supportguy
Presenting SupportGuy, a toolkit that packages the regular grunt work of supporting applications in multiple environments.
This tool is more like a supplement to the Terminal with certain features that meets the requirements of regular support duty, namely

###Tech stack:
* Simple Framework,a Java based HTTP engine
* JCraft, JSch and JZlib for packet compression support to pure Java SSH systems
* Velocity, for UI templating
* JQuery, for UI javascript
* Bootstrap for UI Styling
* YAML for configuration
* Jlamda and Lombok for boilerplate reduction
* Spring for injection

###Features:
* Logs: download, search, tail and error grouping
* Links: availability of urls plus ability to post if required
* Db: sqls to run against databases to monitor state
* Configuration: to pull configuration files and match them against stored/tracked versions in scm
* 
The above listed are nothing new or technically cool but every application babysitter including myself, uses/builds a bunch of tools/scripts.
To avoid building tools that serve same purpose, I built this SupportGuy,a mashup of select features from Terminal,FTP client, FileDiff, WGET/CURL.
Everything is configurable via YAML like

* machines.yml: label, IP, SSH details, log folders, config folders of remote servers
* logs.yml: regex patterns for identifying thread and timestamp
* scms.yml: stored versions of configuration files in local machine
* links.yml: URLS to hit, both GET/POST
* databases.yml: jdbc urls and access credentials
* sqls.yml: queries to run against configured database servers

###Snaps
![Alt text](http://www.vijayrc.com/posts/supportguy/log-search-0.jpg)
![Alt text](http://www.vijayrc.com/posts/supportguy/log-search-1.jpg)
![Alt text](http://www.vijayrc.com/posts/supportguy/log-error.jpg)
![Alt text](http://www.vijayrc.com/posts/supportguy/log-tail.jpg)
![Alt text](http://www.vijayrc.com/posts/supportguy/config.jpg)
![Alt text](http://www.vijayrc.com/posts/supportguy/link.jpg)
![Alt text](http://www.vijayrc.com/posts/supportguy/db.jpg)







