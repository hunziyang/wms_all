local flag = redis.call('exists',KEYS[1])
if flag == 0 then
    redis.call('set',KEYS[1],1)
    redis.call('expire',KEYS[1],2592000)
    return '1'
else
    local num = redis.call('incr',KEYS[1])
    return tostring(num)
end
