# Distributed wicket app w/o sticky sessions
## No more lost in redirection <sup>[1]</sup>

this is just a poc! Without sticky sessions the synchronization on top
on top session cannot work. So in addition we e.g. a RequestCycleListener
that aquires locks on redis key per session. 

But it shows that wicket is dynamically scalable without sticky sessions ;)
 
In addition this approach is independent from a special session storage. 
You can use a external database like redis or a embedded (distributed) cache
like memcached to distribute your session over a cluster.

[1] <https://ci.apache.org/projects/wicket/guide/7.x/guide/redirects.html>
 
  
