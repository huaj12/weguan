DELIMITER $$
CREATE FUNCTION Distance(a POINT, b POINT)  
    RETURNS double DETERMINISTIC
BEGIN
    RETURN GLength(LineStringFromWKB(LineString(a, b)));
END $$
DELIMITER $$

