Hi!
Thanks for letting me take this test, these are always enjoyable.

I have a few bullets about what was/wasn't done, and what I'd do with more time:

- I'm sure that you'll have questions about me injecting VMs via factories the old Dagger way,
instead of using @HiltViewModel. However, @HiltViewModel does not fly with @AssistedInject, and so
I removed @HiltViewModel from all 3 viewModels for consistency. There might be a more proper Hilt-y
solution for that but my Hilt knowledge is admittedly a bit less than my Dagger knowledge.

- There are things I like about my continuous scroll solution (at the data layer), however there's
definitely a bug where, if the day rolls over, the user could potentially miss a day of shifts.
Maybe it would be better to record the most recent "week starting" and increment based on that. But,
it is what it is.

- My NavBus might seem like overkill for how little navigation there is in the app, but I like to
use it even in very small apps, because it's never let me down and I feel that it is an elegant
solution to the "nav in MVVM" inconsistency across the community. That being said, I'm basically
implementing my own SingleLiveEvent here, and my implementation of this *should* be updated to use
a Channel.asFlow, which removes the need to reset state and *actually* solves the SingleLiveEvent
conundrum.

- I notice in the real app, you replace the entire recycler with placeholder items while you load a
new week. I'm not sure if I'm happy with my solution saving the recycler state before adding new
items, then reloading it, but it does at least keep the user where they were, without spending a
bunch of time looking for the perfect solution.

- My tests are dumb, and I will admit that I am not the best tester. I've spent time learning on my
own, but basically every project I've ever worked on has been such an
urgent/chaotic/oldBadArchitecture that applying my testing knowledge in practice has been a bit
difficult.

- Please forgive my UI, I am *not* a designer.

- I'm really excited to hear that you use Compose. I have spent a little bit of personal development
learning, but to be honest did not feel like a 4 hour code test was the right time to learn+apply
the rest of it. So, you're stuck with my databinding for now.

- I have made zero considerations for landscape. It looks like it works fine, and does indeed
remember scroll state between orientation changes. But maybe don't look too deep there.

- I tried to partially replicate your UI. I didn't feel that, with everything else I did, 4 hours
was enough to spend time dealing with the date separators in the list, so I just stuck the date on
each item.

-  I noticed the api stopped returning results for a little bit on Sunday. Maybe that was just
cleanup. Hopefully I didn't impact anything when I put `canScrollVertically` instead of
`!canScrollVertically` and polled the api on every single `onScrolled` event. Sorry about that.