#!/bin/bash

printf "[\n"
break=""
for i in `find ./shared/build/bin -name \*.h`; do
  printf "  %s{\n    \"size\": %s,\n    \"file\": \"%s\",\n    \"date\": \"%s\"\n  }%s\n" "$break" "`cat $i | wc -l | xargs`" "$i" "`date`"
  break=","
done
printf "]\n"