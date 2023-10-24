nums = ["", "one", "two", "three", "four", "five",
        "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirteen", "fourteen", "fifteen",
        "sixteen", "seventeen", "eighteen", "nineteen"]
        
tens = ["", "", "twenty", "thirty", "forty", "fifty",
        "sixty", "seventy", "eighty", "ninety"]


def convert_n_to_word(n):
    if n == 1000:
        return "onethousand"
    elif n >= 100:
        return nums[n // 100] + "hundred" + (("and" + convert_n_to_word(n % 100)) if (n % 100 != 0) else "")
    elif n >= 20:
        return tens[n // 10] + (nums[n % 10] if (n % 10 != 0) else "")
    elif n > 0:
	    return nums[n]


print(sum(len(convert_n_to_word(i)) for i in range(1, 1001)))
