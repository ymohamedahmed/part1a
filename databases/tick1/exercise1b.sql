select title, genre
from movies
where id in(
    select id
    from movies
    join genres as G1 on G1.genre = 'Romance'
    join genres as G2 on G2.genre = 'Comedy'
    where G1.movie_id = G2.movie_id AND id = G1.movie_id 
) 
join genres on movie_id = id and genre <> 'Romance' and genre <> 'Comedy'
order by title, genre;
