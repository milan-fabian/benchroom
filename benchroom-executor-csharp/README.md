# Benchroom C# executor

Command line executor for Benchroom.

Currently usable only on Windows (no Mono support). Platform WINDOWS_X86_64 is hardcoded.

Available command line arguments:

```
 -s, --server      Required. URL of the Benchroom server

 -i, --id          Required. ID of the benchmark to run

 -d, --dir         (Default: run) Directory where to run

 -n, --runs        (Default: 1) How many times to run each combination

 -v, --verbose     (Default: False) Print output from the run program

 -t, --test        (Default: False) Don't send results to server

 -p, --priority    Required. Minimal priority for parameters

 --help            Display this help screen.
```
