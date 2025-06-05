LEFT JOIN: This command would returns all rows from the left table (games),
and the matched rows from the right table (city).
If there's no match, you'll still see the left table row, and null for the right-side columns.

RIGHT JOIN: this command would return all rows from the right table (city),
and the matched rows from the left table (games).
If there's no match, you'll still see the right table row, and null for the left-side columns.

--LEFT JOIN QUERY

SELECT g.yr, g.city, c.country
FROM games g
LEFT JOIN city c ON g.city = c.name;


--RIGHT JOIN QUERY

SELECT g.yr, g.city, c.country
FROM games g
RIGHT JOIN city c ON g.city = c.name;
