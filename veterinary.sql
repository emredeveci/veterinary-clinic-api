PGDMP      ;                |         
   veterinary    16.2    16.2 G    ,           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            -           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            .           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            /           1262    18862 
   veterinary    DATABASE     �   CREATE DATABASE veterinary WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE veterinary;
                postgres    false            �            1259    19119    animal    TABLE     S  CREATE TABLE public.animal (
    id bigint NOT NULL,
    breed character varying(255) NOT NULL,
    color character varying(255) NOT NULL,
    date_of_birth date NOT NULL,
    gender character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    species character varying(255) NOT NULL,
    customer_id integer NOT NULL
);
    DROP TABLE public.animal;
       public         heap    postgres    false            �            1259    19118    animal_customer_id_seq    SEQUENCE     �   CREATE SEQUENCE public.animal_customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.animal_customer_id_seq;
       public          postgres    false    217            0           0    0    animal_customer_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.animal_customer_id_seq OWNED BY public.animal.customer_id;
          public          postgres    false    216            �            1259    19117    animal_id_seq    SEQUENCE     v   CREATE SEQUENCE public.animal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.animal_id_seq;
       public          postgres    false    217            1           0    0    animal_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.animal_id_seq OWNED BY public.animal.id;
          public          postgres    false    215            �            1259    19131    appointment    TABLE     �   CREATE TABLE public.appointment (
    id bigint NOT NULL,
    appointment_date timestamp(6) without time zone NOT NULL,
    animal_id integer NOT NULL,
    doctor_id integer NOT NULL
);
    DROP TABLE public.appointment;
       public         heap    postgres    false            �            1259    19129    appointment_animal_id_seq    SEQUENCE     �   CREATE SEQUENCE public.appointment_animal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.appointment_animal_id_seq;
       public          postgres    false    221            2           0    0    appointment_animal_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.appointment_animal_id_seq OWNED BY public.appointment.animal_id;
          public          postgres    false    219            �            1259    19130    appointment_doctor_id_seq    SEQUENCE     �   CREATE SEQUENCE public.appointment_doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.appointment_doctor_id_seq;
       public          postgres    false    221            3           0    0    appointment_doctor_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.appointment_doctor_id_seq OWNED BY public.appointment.doctor_id;
          public          postgres    false    220            �            1259    19128    appointment_id_seq    SEQUENCE     {   CREATE SEQUENCE public.appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.appointment_id_seq;
       public          postgres    false    221            4           0    0    appointment_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.appointment_id_seq OWNED BY public.appointment.id;
          public          postgres    false    218            �            1259    19141    available_date    TABLE     �   CREATE TABLE public.available_date (
    id bigint NOT NULL,
    available_date date NOT NULL,
    doctor_id integer NOT NULL
);
 "   DROP TABLE public.available_date;
       public         heap    postgres    false            �            1259    19140    available_date_doctor_id_seq    SEQUENCE     �   CREATE SEQUENCE public.available_date_doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.available_date_doctor_id_seq;
       public          postgres    false    224            5           0    0    available_date_doctor_id_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.available_date_doctor_id_seq OWNED BY public.available_date.doctor_id;
          public          postgres    false    223            �            1259    19139    available_date_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.available_date_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.available_date_id_seq;
       public          postgres    false    224            6           0    0    available_date_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.available_date_id_seq OWNED BY public.available_date.id;
          public          postgres    false    222            �            1259    19149    customer    TABLE       CREATE TABLE public.customer (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL
);
    DROP TABLE public.customer;
       public         heap    postgres    false            �            1259    19148    customer_id_seq    SEQUENCE     x   CREATE SEQUENCE public.customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.customer_id_seq;
       public          postgres    false    226            7           0    0    customer_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;
          public          postgres    false    225            �            1259    19158    doctor    TABLE       CREATE TABLE public.doctor (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL
);
    DROP TABLE public.doctor;
       public         heap    postgres    false            �            1259    19157    doctor_id_seq    SEQUENCE     v   CREATE SEQUENCE public.doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.doctor_id_seq;
       public          postgres    false    228            8           0    0    doctor_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.doctor_id_seq OWNED BY public.doctor.id;
          public          postgres    false    227            �            1259    19168    vaccine    TABLE     �   CREATE TABLE public.vaccine (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    protection_end_date date NOT NULL,
    protection_start_date date NOT NULL,
    animal_id integer NOT NULL
);
    DROP TABLE public.vaccine;
       public         heap    postgres    false            �            1259    19167    vaccine_animal_id_seq    SEQUENCE     �   CREATE SEQUENCE public.vaccine_animal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.vaccine_animal_id_seq;
       public          postgres    false    231            9           0    0    vaccine_animal_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.vaccine_animal_id_seq OWNED BY public.vaccine.animal_id;
          public          postgres    false    230            �            1259    19166    vaccine_id_seq    SEQUENCE     w   CREATE SEQUENCE public.vaccine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.vaccine_id_seq;
       public          postgres    false    231            :           0    0    vaccine_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.vaccine_id_seq OWNED BY public.vaccine.id;
          public          postgres    false    229            n           2604    19122 	   animal id    DEFAULT     f   ALTER TABLE ONLY public.animal ALTER COLUMN id SET DEFAULT nextval('public.animal_id_seq'::regclass);
 8   ALTER TABLE public.animal ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    217    217            o           2604    19123    animal customer_id    DEFAULT     x   ALTER TABLE ONLY public.animal ALTER COLUMN customer_id SET DEFAULT nextval('public.animal_customer_id_seq'::regclass);
 A   ALTER TABLE public.animal ALTER COLUMN customer_id DROP DEFAULT;
       public          postgres    false    216    217    217            p           2604    19134    appointment id    DEFAULT     p   ALTER TABLE ONLY public.appointment ALTER COLUMN id SET DEFAULT nextval('public.appointment_id_seq'::regclass);
 =   ALTER TABLE public.appointment ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    218    221            q           2604    19135    appointment animal_id    DEFAULT     ~   ALTER TABLE ONLY public.appointment ALTER COLUMN animal_id SET DEFAULT nextval('public.appointment_animal_id_seq'::regclass);
 D   ALTER TABLE public.appointment ALTER COLUMN animal_id DROP DEFAULT;
       public          postgres    false    221    219    221            r           2604    19136    appointment doctor_id    DEFAULT     ~   ALTER TABLE ONLY public.appointment ALTER COLUMN doctor_id SET DEFAULT nextval('public.appointment_doctor_id_seq'::regclass);
 D   ALTER TABLE public.appointment ALTER COLUMN doctor_id DROP DEFAULT;
       public          postgres    false    221    220    221            s           2604    19144    available_date id    DEFAULT     v   ALTER TABLE ONLY public.available_date ALTER COLUMN id SET DEFAULT nextval('public.available_date_id_seq'::regclass);
 @   ALTER TABLE public.available_date ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    224    224            t           2604    19145    available_date doctor_id    DEFAULT     �   ALTER TABLE ONLY public.available_date ALTER COLUMN doctor_id SET DEFAULT nextval('public.available_date_doctor_id_seq'::regclass);
 G   ALTER TABLE public.available_date ALTER COLUMN doctor_id DROP DEFAULT;
       public          postgres    false    223    224    224            u           2604    19152    customer id    DEFAULT     j   ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);
 :   ALTER TABLE public.customer ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    226    226            v           2604    19161 	   doctor id    DEFAULT     f   ALTER TABLE ONLY public.doctor ALTER COLUMN id SET DEFAULT nextval('public.doctor_id_seq'::regclass);
 8   ALTER TABLE public.doctor ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    228    227    228            w           2604    19171 
   vaccine id    DEFAULT     h   ALTER TABLE ONLY public.vaccine ALTER COLUMN id SET DEFAULT nextval('public.vaccine_id_seq'::regclass);
 9   ALTER TABLE public.vaccine ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    229    231    231            x           2604    19172    vaccine animal_id    DEFAULT     v   ALTER TABLE ONLY public.vaccine ALTER COLUMN animal_id SET DEFAULT nextval('public.vaccine_animal_id_seq'::regclass);
 @   ALTER TABLE public.vaccine ALTER COLUMN animal_id DROP DEFAULT;
       public          postgres    false    231    230    231                      0    19119    animal 
   TABLE DATA           e   COPY public.animal (id, breed, color, date_of_birth, gender, name, species, customer_id) FROM stdin;
    public          postgres    false    217   #Q                 0    19131    appointment 
   TABLE DATA           Q   COPY public.appointment (id, appointment_date, animal_id, doctor_id) FROM stdin;
    public          postgres    false    221   �Q       "          0    19141    available_date 
   TABLE DATA           G   COPY public.available_date (id, available_date, doctor_id) FROM stdin;
    public          postgres    false    224   ]R       $          0    19149    customer 
   TABLE DATA           I   COPY public.customer (id, address, city, email, name, phone) FROM stdin;
    public          postgres    false    226   �R       &          0    19158    doctor 
   TABLE DATA           G   COPY public.doctor (id, address, city, email, name, phone) FROM stdin;
    public          postgres    false    228   �S       )          0    19168    vaccine 
   TABLE DATA           h   COPY public.vaccine (id, code, name, protection_end_date, protection_start_date, animal_id) FROM stdin;
    public          postgres    false    231   �T       ;           0    0    animal_customer_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.animal_customer_id_seq', 1, false);
          public          postgres    false    216            <           0    0    animal_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.animal_id_seq', 12, true);
          public          postgres    false    215            =           0    0    appointment_animal_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.appointment_animal_id_seq', 1, false);
          public          postgres    false    219            >           0    0    appointment_doctor_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.appointment_doctor_id_seq', 1, false);
          public          postgres    false    220            ?           0    0    appointment_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.appointment_id_seq', 8, true);
          public          postgres    false    218            @           0    0    available_date_doctor_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.available_date_doctor_id_seq', 1, false);
          public          postgres    false    223            A           0    0    available_date_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.available_date_id_seq', 16, true);
          public          postgres    false    222            B           0    0    customer_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.customer_id_seq', 7, true);
          public          postgres    false    225            C           0    0    doctor_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.doctor_id_seq', 7, true);
          public          postgres    false    227            D           0    0    vaccine_animal_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.vaccine_animal_id_seq', 1, false);
          public          postgres    false    230            E           0    0    vaccine_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.vaccine_id_seq', 14, true);
          public          postgres    false    229            z           2606    19127    animal animal_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT animal_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.animal DROP CONSTRAINT animal_pkey;
       public            postgres    false    217            |           2606    19138    appointment appointment_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.appointment DROP CONSTRAINT appointment_pkey;
       public            postgres    false    221            ~           2606    19147 "   available_date available_date_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.available_date
    ADD CONSTRAINT available_date_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.available_date DROP CONSTRAINT available_date_pkey;
       public            postgres    false    224            �           2606    19156    customer customer_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
       public            postgres    false    226            �           2606    19165    doctor doctor_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_pkey;
       public            postgres    false    228            �           2606    19176    vaccine vaccine_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.vaccine
    ADD CONSTRAINT vaccine_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.vaccine DROP CONSTRAINT vaccine_pkey;
       public            postgres    false    231            �           2606    19182 '   appointment fk2kkeptdxfuextg5ch7xp3ytie    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fk2kkeptdxfuextg5ch7xp3ytie FOREIGN KEY (animal_id) REFERENCES public.animal(id);
 Q   ALTER TABLE ONLY public.appointment DROP CONSTRAINT fk2kkeptdxfuextg5ch7xp3ytie;
       public          postgres    false    4730    217    221            �           2606    19177 "   animal fk6pvxm5gfjqxclb651be9unswe    FK CONSTRAINT     �   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT fk6pvxm5gfjqxclb651be9unswe FOREIGN KEY (customer_id) REFERENCES public.customer(id);
 L   ALTER TABLE ONLY public.animal DROP CONSTRAINT fk6pvxm5gfjqxclb651be9unswe;
       public          postgres    false    217    226    4736            �           2606    19192 *   available_date fkk0d6pu1wxarsoou0x2e1cc2on    FK CONSTRAINT     �   ALTER TABLE ONLY public.available_date
    ADD CONSTRAINT fkk0d6pu1wxarsoou0x2e1cc2on FOREIGN KEY (doctor_id) REFERENCES public.doctor(id);
 T   ALTER TABLE ONLY public.available_date DROP CONSTRAINT fkk0d6pu1wxarsoou0x2e1cc2on;
       public          postgres    false    228    4738    224            �           2606    19197 #   vaccine fkne3kmh8y5pcyxwl4u2w9prw6j    FK CONSTRAINT     �   ALTER TABLE ONLY public.vaccine
    ADD CONSTRAINT fkne3kmh8y5pcyxwl4u2w9prw6j FOREIGN KEY (animal_id) REFERENCES public.animal(id);
 M   ALTER TABLE ONLY public.vaccine DROP CONSTRAINT fkne3kmh8y5pcyxwl4u2w9prw6j;
       public          postgres    false    217    4730    231            �           2606    19187 '   appointment fkoeb98n82eph1dx43v3y2bcmsl    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fkoeb98n82eph1dx43v3y2bcmsl FOREIGN KEY (doctor_id) REFERENCES public.doctor(id);
 Q   ALTER TABLE ONLY public.appointment DROP CONSTRAINT fkoeb98n82eph1dx43v3y2bcmsl;
       public          postgres    false    228    221    4738               �   x�5�K�� �ᵹ# M��K7.q
/A������e$�sT��P���q�p!��#�lX�O��T��:^vv��S�P�~�b�S!Й<]���;ӷ��
]K6CI{0k�J����H�9;A����o���z\�ɧ�~/Q�)Ƶ=�r|�+>q1s5��É-k4�K����L&�+r����rV0��{�0�� �_G         N   x�U���0��]E0�]b9P��A�i#-��ap�r_	�MF	��'�hȒ.Cs�^r.�%��7J�}��.��      "   `   x�M��� �7ޅ
��.��&�B�'�N$I�,�L�:Yz.#}��@R܇t��z�?Exc�-�y:�?���p=�c�m��-����7���\ ^��!�      $   �   x�U��n�0�g�)���]�n�ԪK�J�����r'޾��C-{�ϲ����<qD�a�c?{@	�~��b5� �;����`ZS�����;�G�9B�0����:��ʧ�`I����R��pqI[�m�S�y�0�E�r�	3[:�!�F|�7�1f�2�&�"����=�NR�8<v?Ѯ��ij�W���	.\V�w���CF��G!�[{�ڐ����+gc����R���j�      &   �   x�E��n� E��W�Q�CuvTQ���ȮR7�xb�m�����M�f�As��#q�ۍ ݄��h�f;Ot����s�jUWM��
a���{��FA_=ǝ�hbQ|&&��Y�<�M�6]-���Y��w���?Ə7����v�������{yz�T%�R���{��؁����gJn.��������GՉ��芫C��@:�.ZW�_S�/9���j򈯃���n�      )   �   x�u�K� ����]03<���XL�bK�x{��֍+~��$\_D�eɗ��d�Z�%QA���4d�pߡݡ�]��/�j*���
%mK2����������Ҵ��Y��}}��s!F�a^�s�P٧8��
��bx�s
��v�^�K�����kCU     