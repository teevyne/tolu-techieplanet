SELECT g.yr, g.city, c.country
FROM games g
JOIN city c ON g.city = c.name;