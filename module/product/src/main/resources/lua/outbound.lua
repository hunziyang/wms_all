for i = 1, #KEYS do
    if redis.call('exists',KEYS[i]) == 0 then
       return KEYS[i]
    end
    local num = tonumber(redis.call('get',KEYS[i]))
    if tonumber(ARGV[i]) > num then
        return KEYS[i]
    end
end
for i = 1, #KEYS do
    redis.call('decrby', KEYS[i], tonumber(ARGV[i]))
end
return 'success'