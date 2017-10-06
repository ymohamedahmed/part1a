select title
from Movies
join genres as G1 on G1.genre = 'Romance'
join genres as G2 on G2.genre = 'Comedy'
where G1.movie_id = G2.movie_id AND id = G1.movie_id
order by title;
