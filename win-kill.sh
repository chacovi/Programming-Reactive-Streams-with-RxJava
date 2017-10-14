jps | grep Service | cut -f 1 -d ' ' | xargs -n 1 taskkill -f -pid
