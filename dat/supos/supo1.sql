select title, 
    --calculate number of actors in common then times by 10
    --note 4119380 is the movie id of skyfall
    (select count(*)
    from credits
    inner join 
        (select person_id 
        from credits
        where type = 'actor'
        and movie_id = 4119380
        ) t1 on t1.person_id = credits.person_id and type='actor' and credits.movie_id=movies.id)*10 +
    --add to the number of keywords in common to give the score
    (select count(*)
    from keywords
    inner join
        (select keyword 
        from keywords
        where movie_id = 4119380
        ) t2 on t2.keyword = keywords.keyword and keywords.movie_id = movies.id) as score
from movies
--exclude skyfall itself
where title<>'Skyfall (2012)'
--count the number of genres in common and check it's more than 0 
and (select count(*)
    from genres
    inner join
        (select genre
        from genres
        where movie_id = 4119380) t3 on t3.genre = genres.genre and genres.movie_id = movies.id) >= 1
--check the number of actors in common is more than 0
and (select count(*)
    from credits
    inner join 
        (select person_id 
        from credits
        where type = 'actor'
        and movie_id = 4119380
        ) t1 on t1.person_id = credits.person_id and type='actor' and credits.movie_id=movies.id) >= 1
--check the number of keywords in common is more than 0
and (select count(*)
    from keywords
    inner join
        (select keyword 
        from keywords
        where movie_id = 4119380
        ) t2 on t2.keyword = keywords.keyword and keywords.movie_id = movies.id) >= 1
--order the results and sort them
order by score desc
limit 10;



