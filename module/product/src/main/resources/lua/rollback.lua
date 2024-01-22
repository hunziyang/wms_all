local count = redis.call('get',KEYS[1])
if count == 0 then
    return 'fail'
end
local count = tonumber(ARGV[1])
redis.call('decrby',KEYS[1],count)
return 'success'