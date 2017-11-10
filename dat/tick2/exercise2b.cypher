match (p:Person) - [:DIRECTED] -> (m:Movie) <- [:PRODUCED] - (p:Person),
(p:Person) - [:ACTS_IN] -> (m:Movie) <- [:EDITED] - (p:Person)
return p.name as name, m.title as title
order by name, title;
