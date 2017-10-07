select title, count(*) as total
from movies
join keywords on movie_id = id
where title<>'Skyfall (2012)' and keyword in(
    select keyword
    from keywords
    join movies on movie_id = id
    where title = 'Skyfall (2012)'
)
group by title
order by total desc
limit 10;
