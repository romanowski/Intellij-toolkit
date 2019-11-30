# Profile highlights

For now we are able to capture times needed to compute highlights for given editor.

It captures all time needed to prepare all highlights but we can expand it to target specific passes (e.g. scala ones).

Example output (comments are added later on manually):

```

// Open MimaExceptionsTest.scala
Analysis took 31559 ms for /home/krzysiek/workspace/cats/binCompatTest/src/test/scala/catsBC/MimaExceptionsTest.scala
Analysis took 163 ms for /home/krzysiek/workspace/cats/binCompatTest/src/test/scala/catsBC/MimaExceptionsTest.scala
Analysis took 103 ms for /home/krzysiek/workspace/cats/binCompatTest/src/test/scala/catsBC/MimaExceptionsTest.scala
Analysis took 15 ms for /home/krzysiek/workspace/cats/binCompatTest/src/test/scala/catsBC/MimaExceptionsTest.scala
Analysis took 9 ms for /home/krzysiek/workspace/cats/binCompatTest/src/test/scala/catsBC/MimaExceptionsTest.scala

// Open future.scala
Analysis took 6060 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 106 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 57 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 98 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 30 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 83 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
// Start changing code in future.scala (introduce problems, add calls et
Analysis took 702 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 65 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 640 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 82 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 49 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 202 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 37 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 190 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 637 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 20 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 50 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
Analysis took 607 ms for /home/krzysiek/workspace/cats/js/src/main/scala/cats/js/instances/future.scala
```

It works by providing to 2 phases: 
- Before - that should be start first and has not dependencies. It stores information when it was started
- After - that is started as last and depends on all other phases (first 200 at least)  and reads information from Before phase and compute time

This is not 100% reliable mechanism since one phase may not be run (canceled) or there is other problems (since all this is asychronous and there is no way to have proper uuid to track)

# Capture statistics

Intellij has built-in system to upload various statistics to Jetbrains servers

If enabled Intellij gathers in files (e.g. `6163-40f6-839e-3373be65bfa8-193.5233.102-release.log`) in `<idea-home>/.IdeaIC2019.3/system/event-log` directory.
`<idea-home>` is directory that stores all Intellij settings (e.g. `~/.IdeaIC2019.3/` on my machine). Later on Statics are periodically send back to servers.

Example stats (only from pefromance group) can be found [here](example_log.json).

The only 'official' option is to enable both capturing and sending stats is `Send usage statistic` setting and in many cases this is not possible due to firewalls or corporate policies.

There is a way to enable just gathering however it is not official. It can be done by adding following line: `idea.local.statistics.without.report=true` to `<intellij-install-dir>/bin/idea.properties`.

Moreover there is a way to consume stats from given plugins and [Stats.scala](src/main/scala/com/github/romanowski/intellij/toolkit/Stats.scala) prints all events from `performance` category on stdout.
