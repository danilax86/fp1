iterations = dict()

def sequence(n, iterations):
    number = n
    length = 1
    
    while n != 1:
        if n in iterations:
            length += iterations[n] - 1
            break
        if n % 2 == 0:
            n = n // 2
            length += 1
        else:
            n = 3*n + 1
            length += 1
    iterations[number] = length
    
    return length

final_len = 1
for i in range(1,1000001):
    temp_len = sequence(i, iterations)
    if  temp_len > final_len:
        final_len = temp_len
        result = i

print(result)
