                                                PROPOSED TESTS ANALYSIS
                                              ---------------------------


-LouvainAlgorithmTest01: This test applies the louvain algorithm to the example graph from the article "Fast unfolding of
    communities on large networks" which consists in a circle of 30 5-cliques joined together with a 1-weight edge. You
    can see that the result is the same as the book. This entry is very long(1600 lines) so it was generated with a python
    script that allows you to generate circles of n N-cliques. You can find it on the script LDGraphGenerator.py. See the
    end of the file or execute it with "h" as its first argument to see how it works.

-LouvainAlgorithmTest02: This test applies the louvain algorithm to the example graph from the article "Fast unfolding of
    communities on large networks" page 3. Graph contains 16 nodes with some edges between all with weight 1. The result is the same
    as the document - two communities with nodes 0-7 and 8-15.

-GeneralDriverTest02: This test is a slight approximation to a big case (i.e. the hole real congress). In order to achieve it
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

-GeneralDriverTest05: This test generates a congress of 20 MPs with just 4 basic attributes (Gender, Age, Ethnicity and
    Party). What we want to check here is the capacity of Louvain's algorithm to change the communities depending on which
    attributes are defined and which are their importances. So, first, we will obtain that the communities are separated by
    the Gender and the Party, except for the women community that, as it is not too big, joins republican and democratic women
    MPs in one hole community. Secondly, we will reduce the power of Gender in that decision and add some new powerful
    attributes, like FavouriteBook or FavouriteMusic, but then, we will get two communities (the republican and the democratic),
    because of their importance and because of the fact that ALL MPs have a defined value for them. Hence, at that point
    we conclude that the attributes that play the strongest paper on Louvain's community detection are the ones that have
    a high importance and that are present in all the members of the parliament. Finally, we will invalidate (importance=0)
    all the attributes, except for the Gender, the Age and the Ethnicity, which will lead us to obtein a two-sided division,
    men vs women, as values for Age and Ethnicity are more variable.

-GeneralDriverTest06: This test should guess result of votes for internet regulation law and try to guess how much parties
    affect MPs opinions. We used attributes with importances: public regulation opinion 3 (obviously high importance but quite
    lot of MPs doesn't have it), social network activity 2 (suppose internet active people will be against regulation),
    age 2 (suppose younger person will vote against law), graduated in 2 (suppose that technical field will be against),
    party 2 (it is described more later), religion 1 (it is not that important in this law), race 1 (not important much).
    We added a few MPs with attributes and simply compute weights and then communities. We can see three communities more or less
    group for law, against and group they don't have opinion yet. After we changed importance to party from 2 to 3 and compute again.
    We can see only two communities. People which didn't know before "decided" by party. It can be useful after votes
    we can compare these two results and determine how much parties affect opinions of MPs in the real world.

-AttributeDriverTest01: It tests basic functionality of attributes and manipulating with them and MPs.
    It creates three new attributes definitions (religion, sex and party) add some values to prearranged MPs and show
    list of attribute definitions and MPs with their attributes. Than the test removes attribute sex from MP with ID 1
    and party from MP with ID 3 After that it shows list of MPs and their attributes for check. Than it removes attribute
    definition sex and again shows lists of attribute definitions and MPs with their attributes.

-MPDriverTest01: Very similar to attribute driver.
    The aim of that test is checking that everything works fine, and also the error control and exceptions handling. After
    creating a congress of 17 MPs, it defines 10 basic attributes (Gender, Age, Religion, Favourite color, Social status,
    Ethnicity, University, Graduated in, Favourite sport and Party), which allows you to play a little bit with their
    importances and values.
-CompareDriverTest01:Just a simple text to show that it calculates the improvement percentage correctly. All of the other
    functionalities are tested on the Louvain driver so not much complexity is needed