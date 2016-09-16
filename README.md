# Distributed wicket app w/o sticky sessions
## No more lost in redirection <sup>1</sup>

This is just a PoC! Without sticky sessions wickets synchronization via 
`org.apache.wicket.page.PageAccessSynchronizer` cannot work because it 
is stored into session object. If session is deserialized on another VM,
the current in-memory approach cannot work. 
So in addition we need a custom `PageAccessSynchronizer` implementation 
that aquires locks on a redis key per page access per session. 

But it shows that wicket is dynamically scalable without sticky sessions ;)
 
In addition this approach is independent from a special session storage. 
You can use an external database like redis or an embedded (distributed) cache
like memcache to distribute your session over a cluster.

[1] <https://ci.apache.org/projects/wicket/guide/7.x/guide/redirects.html>
 
  
