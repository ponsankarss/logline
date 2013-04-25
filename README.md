logline - Visualization/analysis of logs
=======

Logging has now got its rightful attention, best practices are listed everywhere by people, log analysers are flooding the market
My favorite rule on logging is to append a unique-id for every logical unit of work. This unique-id should enable mapping to some valid business entity like a userid, a claim-number in a healthcare system or a call-id in IVR system. Usually defect tickets are raised mentioning just this business entity and a time window of its occurence. Now with logs that lack this mapping, the support guy must wade through a tangle of intermingling logs with multiple grep commands to pick that specific thread of error occurrence.
In this post I wish to share a small tool I built to analyse application logs from systems I support. Been using a bunch of custom grep scripts to detect issues, but I was not able to share with Windows users. Requesting them to install zygwin and all dependent packages will lead to huge acceptance inertia, so decided to make it a browser application backed by an embedded server. Tried tools like Splunk, Chainsaw but found that huge effort is required to tailor them a lot to fit my team requirements.

![Alt text](http://vijayrc.com//posts//logline/logline.jpg)

###The tool offers the following features
* Fast and selective download of logs from remote machines using stream compression, takes usually 10% of time taken by FileZilla, WinSCP.
Mutiple regex search keys
* Grouping by threads
* Highlighting key events like SQL, JMS message posting, text-of-interest like those uniqueids I mentioned before
* Finding XML patterns in logs and opening them on demand. This was required to supress xml noise in our logs.
* Flexible context size around search patterns like grep
* Identify exception stacktraces, group them by timestamp and display in tabs.
* Simple packaging, only a single executable jar to get a running webapp.
* Time window to filter logs

###Tech stack
* Simple Framework,a Java based HTTP engine
* JCraft, JSch and JZlib for packet compression support to pure Java SSH systems
* Velocity, for UI templating
* JQuery, for UI javascript
* Bootstrap for UI Styling

PS:I am yet to restructure the code to make it generic for others to use in binary form, but the code can be reused
