# Lithic

Hi Lithic,

This small piece of software is a coding exercise I asked some candidates to solve and is based on the pricing model of Atlassian products. In the manner of "eat your own dog food", I wrote a version for you.

Here's the original task for javascript candidates:

> We're offering the following flexible per-user pricing, so our clients never pay for more than they need.
> - $10 flat monthly fee for small teams of up to 10 users
> - $7 per user/month for the first 100 users
> - $5 per user/month for the next 150 users
> - $1.10 per user/month for each additional user over 250
> 
> We want to build a function that accepts an integer `users` and returns the final price.
>```js
>pricing(1) // $10
>pricing(10) // $10
>pricing(100) // $700
>pricing(123) // $815
>pricing(1234) // $2,532.40
>```

## Considerations

There's room for improvement, and I will release 1.1.0 with a builder pattern style functionality and some tweaks as soon as I find some time to do it.

Here are some considerations I had during this task:

### Format of input
What interface do I want to provide to other developers? It should be easy to read and easy to work with. Do I use a custom pair object (e.g., Apache Commons tuples, Object arrays...). In the end, I went with a trade-off. I wanted to have fewer dependencies and provide input types that one can use right out-of-the-box (e.g., array). The trade-off is that one needs to understand that the array has semantics. Every two entries are a pair (like key-value pairs in Map.of()). To account for a different usage style, I want to overload the calculate method and provide an alternative with a builder pattern and a custom object.

### Exception handling
What kind of exceptions do I want to cover? Is it runtime or checked exceptions? For now, I check for two argument errors and one early exit. However, I already see that a checked exception that I pass on to the user makes more sense for certain things. This will also be part of the 1.1.0 version.

### Configurability
How configurable shall this utility be? For now, I went with a flag to consider flat pricing as the lowest price. If this flag is set to false, there's no flat pricing, and every tier will be considered equally.

### Sorting of input pairs
Do I want to sort the input, or does this meddle with the expected output of the user? I couldn't see an issue right away, so I went with sorting to have a clean data set for the calculation. Each pair will be sorted by its amount/limit (e.g., price-limit pair).




