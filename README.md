[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e01e379b21444f60971c76a3fe23159e)](https://app.codacy.com/app/YanaKuznetsova/JavaRushInternship-Topjava-graduation?utm_source=github.com&utm_medium=referral&utm_content=YanaKuznetsova/JavaRushInternship-Topjava-graduation&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.com/YanaKuznetsova/JavaRushInternship-Topjava-graduation.svg?branch=master)](https://travis-ci.com/YanaKuznetsova/JavaRushInternship-Topjava-graduation)

<p>Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) <strong>without frontend</strong>.</p>
<p>The task is:</p>
<p>Build a voting system for deciding where to have lunch.</p>
<ul>
<li>2 types of users: admin and regular users</li>
<li>Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)</li>
<li>Menu changes each day (admins do the updates)</li>
<li>Users can vote on which restaurant they want to have lunch at</li>
<li>Only one vote counted per user</li>
<li>If user votes again the same day:
<ul>
<li>If it is before 11:00 we asume that he changed his mind.</li>
<li>If it is after 11:00 then it is too late, vote can't be changed</li>
</ul>
</li>
</ul>
<p>Each restaurant provides new menu each day.</p>
<p>As a result, provide a link to github repository.</p>
<p>It should contain the code and <strong>README.md with API documentation and curl commands to get data for voting and vote.</strong></p>
<hr>
