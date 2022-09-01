create view v_article_deleted_list as
    select ta.article_id, tu.user_id, tu.user_name,
           ta.article_title, ta.article_rank, ta.article_type,
           tt.type_name, ta.article_post_time, ta.article_edit_time,
           ta.article_like_count, ta.article_view_count, ta.article_comment_count,
           ta.article_is_comment
from tbl_article as ta
    join tbl_user tu on tu.user_id = ta.article_author
    join tbl_type tt on ta.article_type = tt.type_id;


create view v_article_get_single as
    select ta.article_id, tu.user_name, ta.article_title,
           ta.article_author, ta.article_html, ta.article_is_comment,
           ta.article_post_time, ta.article_view_count, ta.article_like_count,
           ta.article_comment_count
from tbl_article as ta
     join tbl_user tu on tu.user_id = ta.article_author;


create view v_article_get_single_deleted as
select ta.article_id, tu.user_name, ta.article_title,
       ta.article_author, ta.article_html, ta.article_is_comment,
       ta.article_post_time, ta.article_view_count, ta.article_like_count,
       ta.article_comment_count
from tbl_deleted_article as ta
         join tbl_user tu on tu.user_id = ta.article_author;


create view v_article_list_show as
select ta.article_id, ta.article_author, tu.user_name,
       ta.article_title, ta.article_summary, ta.article_rank,
       ta.article_type, ta.article_view_count, ta.article_like_count,
       ta.article_post_time, ta.article_comment_count
from tbl_article as ta
         join tbl_user tu on tu.user_id = ta.article_author;

create view v_article_manage_list as
    select ta.article_id, tu.user_id, tu.user_name,
           ta.article_title, ta.article_rank, ta.article_type,
           tt.type_name, ta.article_post_time, ta.article_edit_time,
           ta.article_like_count, ta.article_view_count, ta.article_comment_count,
           ta.article_is_comment
    from tbl_article as ta
             join tbl_user tu on tu.user_id = ta.article_author
             join tbl_type tt on ta.article_type = tt.type_id;


create view v_comment_article as
select  tc.comment_article_id, tc.comment_content,
       tc.comment_author_name, tc.comment_date,
       tc.comment_like_count, tc.comment_id
    from tbl_comment tc;

create view v_comment_article_deleted as
select  tc.comment_article_id, tc.comment_content,
        tc.comment_author_name, tc.comment_date,
        tc.comment_like_count, tc.comment_id
from tbl_comment tc;

create view v_comment_manage_list as
    select  tc.comment_id, tc.pre_comment_id, tc.comment_article_id,
           ta.article_id, ta.article_author as user_id,  tc.comment_author_name,
           tc.comment_author_id, tc.comment_author_email, ta.article_title,
           tc.comment_like_count, tc.comment_content, tc.comment_date
from tbl_comment tc join tbl_article ta on tc.comment_article_id = ta.article_id;


create view v_login as
select tul.login_user as user_id,
       tu.user_name,
       tu.user_signature,
       tu.user_password,
       tu.user_role as role_name
from tbl_user_login tul join tbl_user tu on tul.login_user=tu.user_id;

create view v_page_article as
select ta.article_id, ta.article_author, article_title, article_post_time
from tbl_article ta;

create view v_page_comment as
    select tc.comment_author_name,
           tc.comment_article_id as article_id,
           ta.article_author,
           ta.article_title,
           tc.comment_date
from tbl_comment as tc join tbl_article ta on tc.comment_author_id=ta.article_id;

create view v_page_like as
select ta.article_id, ta.article_author, ta.article_title,
       tlr.like_date, tu.user_name
from tbl_like_record as tlr
    join tbl_article ta on tlr.article_id = ta.article_id
    join tbl_user tu on ta.article_author = tu.user_id;

create view v_user_manage_show as
select tu.user_id, tu.user_name,
       tu.user_role, tu.user_create_date,
       tu.user_last_login_date, tu.user_ip
from tbl_user as tu;


create view v_use_total_data as
select ta.article_author, tu.user_name, tu.user_signature,
       count(ta.article_id) as total_article,
       sum(ta.article_view_count) as total_view_count,
       sum(ta.article_like_count) as total_like_count,
       sum(ta.article_comment_count) as total_comment_count
from tbl_user tu join tbl_article ta on tu.user_id = ta.article_author;

