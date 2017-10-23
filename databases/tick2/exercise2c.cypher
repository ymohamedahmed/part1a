match (g1:Genre) <- [:HAS_GENRE] - (m:Movie) <- [:DIRECTED] - (p:Person) - [:ACTS_IN] -> (m:Movie) -[:HAS_GENRE] -> (g2:Genre)
where g1.genre = "Adventure" AND g2.genre = "Action"
return p.name as name, m.title as title
order by name, title;
