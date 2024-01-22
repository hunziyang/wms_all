local flag = redis.call("exists",KEYS[1])
local count = tonumber(ARGV[1])
if flag == 1 then
    redis.call("incrby",KEYS[1],count)
    return 'incrby'
else
    redis.call("set",KEYS[1],count)
    return 'insert'
end
