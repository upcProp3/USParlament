

-LouvainAlgorithmTest01: This test applies the louvain algorithm to the example graph from the article "Fast unfolding of
    communities on large networks" which consists in a circle of 30 5-cliques joined together with a 1-weight edge. You
    can see that the result is the same as the book. This entry is very long(1600 lines) so it was generated with a python
    script that allows you to generate circles of n N-cliques. You can find it on the LouvainDriverCliqueCircleGenerator.py
    file.
-GeneralDriverTest02: This test is a slight approximation to a big case (i.e. the hole congress). In order to achieve it
    we have generated a congress with 50 MPs, one for each possible State, and we have added between 5 and 9 attributes to
    each one, so, at least, each MP should have a defined gender, age, social status and ethnicity. Furthermore, if we look
    at the attributes' importances we can easyly see that the ones for these 5 previous attributes are pretty high, and the
    others, except for the religion, are between 1 and 2. Hence, this test classifies the congressmen and women depending on
    the gender, the age, the social status and the ethnicity, and taking religion into consideration too; which may be quite
    very good approximation of the general groups inside the Parliament.
-GeneralDriverTest03: This test is used to try to find the groups with a tendency to vote the same in an Israel-Related vote.
    Both the Republican and Democratic parties decided not to set an official position.
    There are 15 MPs, with different attributes. The possible attributeDefinitions are Sex,Religion,Party,Gun Control Opinion,
    Race and Upbringing (if they come for a Rich or Poor family). Since it is a vote about palestine, we give a relevance of
    3 to the religion, 1 to the party because even if there is no official they tend to vote together, 2 to the upbringing
    since we think the upbringing can affect the decision of the MPs and 1 to the race.
    When calculating we see that there will mainly be two groups, the first one has the all of the Christian MPs, and the
    other one contains all the jewish MPs (all democrats and a quite a few of humble origins) and the californian mps, which
    are all democrats and of humble origins even though they have no established religion.
    You can see that the result is quite logical given the attributes of each MP.
-GeneralDriverTest04: This test is used to try to find the groups that will vote together in a vote to pass a law to
    increase the control over the police. This vote has mostly been instigated by the black community due to the
    recent increase in violence between the police and the black people.
    There are 13 MPs. The possible attributes are Race, Sex and Police Control (Y if they are in favour of tighter control
    on police officers, N if they are against, nothing if they have no known position). Due to the nature to the vote we
    give the highest value (3) to race and position on police control of the MPs.
    The result gives us 3 communities, the first one contains all the black MPs that want a tighter control on the police
    The second community contains all the white MPs that don't agree to a tighter control over the police plus a black mp
    that shares their opinion on Police Control.
    The third community contains all the latino MPs, two of them don't have an opinion about the control over the police while
    the third (from NM) agrees with the proposition. Even though he agrees it seems that he will tend to vote together with
    the other latinos.
